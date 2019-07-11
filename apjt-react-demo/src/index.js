import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

function Square(props) {
  return (
    <button className="square" onClick={props.onClick}>
      {props.value}
    </button>
  );
}

class Board extends React.Component {
  renderSquare(i) {
    return (
      <Square value={this.props.squares[i]} onClick={() => this.props.onClick(i)}
      />
    );
  }

  render() {
    const squareLength = 3;
    const grid = Array(squareLength).fill(Array(squareLength).fill(''));

    return (
      <div>
        {grid.map((row, rowIndex) => {
          return (
            <div className="board-row">
                {row.map((column, columnIndex) => {
                  return ( this.renderSquare(rowIndex * squareLength + columnIndex) )
                })}
            </div>
          )
        })}
      </div>
    );
  }
}

class Game extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      stepNumber: 0,
      history: [{
        squares: Array(9).fill(null),
        status: this.calculateStatus(undefined, 0),
        terminal: false,
      }],
    };
  }

  player(stepNumber) {
    return stepNumber % 2 == 0 ? 'X' : 'O';
  }

  handleClick(i) {
    const history = this.state.history.slice(0, this.state.stepNumber + 1);
    const current = history[history.length - 1];
    let squares = current.squares;

    if (squares[i] || calculateWinner(squares)) {
      return;
    }

    squares = squares.slice();
    squares[i] = this.player(history.length - 1);

    this.setState({
      stepNumber: this.state.stepNumber + 1,
      history: history.concat([{
        squares: squares,
        status: this.calculateStatus(squares, this.state.stepNumber + 1),
        terminal: false,
      }] ),
    });
  }

  calculateStatus(squares, stepNumber) {
    if (squares) {
      const winner = calculateWinner(squares);

      if (winner) {
        return 'Winner: ' + winner;
      }

      if (squares.every(e => e)) {
        return 'Draw';
      }
    }

    return 'Next player: ' + this.player(stepNumber);
  }

  jumpTo(step) {
    this.setState({
      stepNumber: step,
    });
  }

  render() {
    const history = this.state.history;
    const current = history[this.state.stepNumber];
    const squares = current.squares;

    const moves = history.map((step, move) => {
      const className = move == this.state.stepNumber ? 'current' : '';
      const desc = move ? 'Go to move #' + move : 'Go to game start';
      return (
        <li key={move} className={ className }>
          <button onClick={() => this.jumpTo(move) }>{desc}</button>
        </li>
      );
    });

    return (
      <div className="game">
        <div className="game-board">
          <Board squares={ squares } onClick={ (i) => this.handleClick(i) } />
        </div>
        <div className="game-info">
          <div className="status">{ current.status }</div>
          <ol className="game-history">{ moves }</ol>
        </div>
      </div>
    );
  }
}

function calculateWinner(squares) {
  const lines = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [2, 4, 6],
  ];
  for (let i = 0; i < lines.length; i++) {
    const [a, b, c] = lines[i];
    if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
      return squares[a];
    }
  }
  return null;
}

// ========================================

ReactDOM.render(
  <Game />,
  document.getElementById('root')
);
