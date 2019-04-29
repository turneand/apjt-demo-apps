package com.apjt.dto;

/**
 * Simple DTO object representing a greeting.
 */
public class Greeting {
	private final long id;
	private final String message;

	public Greeting(final long id,
					final String message) {
		this.id = id;
		this.message = message;
	}

	public long getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}	
}
