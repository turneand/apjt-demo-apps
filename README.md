# apjt-dropwizard-demo
Sample application demonstrating some basic dropwizard functionality

## To build...
```
mvn verify -Drevision=2.0.1
docker build --tag apjt-dropwizard-demo:latest .
```

## To run locally...
```
docker run -p 9092:8080 apjt-dropwizard-demo:latest
```

# To connect to running instance

To connect to an already running instance:
1. Use "docker ps" to get the CONTAINER ID of the image you want to connect to
2. Run the following command where <CONTAINER ID> is from the above step
```
docker exec -it <CONTAINER ID>  /bin/ash
```

To start a new instance without the service running (useful for startup issues)
```
docker run -it --entrypoint /bin/ash apjt-dropwizard-demo:latest -i
```

## Useful URLs when running:

| URL                                               | Description                                                    |
| ------------------------------------------------- | -------------------------------------------------------------- |
| http://localhost:9092/greeting/sync               | A basic blocking call                                          |
| http://localhost:9092/greeting/async-manual       | A basic non-blocking call, with a manually managed thread pool |
| http://localhost:9092/greeting/async-managed      | A basic non-blocking call, with a jetty managed thread pool    |

## Source URLs

Links to the external documentation that was used as additional content to this codebase

| URL                                                                            | Description                                                         |
| ------------------------------------------------------------------------------ | ------------------------------------------------------------------- |
| https://www.dropwizard.io/1.3.9/docs/getting-started.html                      | The official getting started guide for dropwizard                   |
| https://jeremylong.github.io/DependencyCheck/dependency-check-maven/index.html | The maven plugin used to analyse the dependency vulnerabilities     |
| https://maven.apache.org/maven-ci-friendly.html                                | How to pass versions numbers into maven without the versions plugin |
