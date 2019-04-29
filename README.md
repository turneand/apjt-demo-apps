# apjt-spring-boot-demo
Sample application demonstrating some basic spring-boot functionality

## To build...
```
mvn verify -Drevision=1.0.1
docker build --tag apjt-spring-boot-demo:latest .
```

## To run locally...
```
docker run -p 9091:8080 apjt-spring-boot-demo:latest
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
docker run -it --entrypoint /bin/ash apjt-spring-boot-demo:latest -i
```

## Useful URLs when running:

| URL                                               | Description                                                                                           |
| ------------------------------------------------- | ----------------------------------------------------------------------------------------------------- |
| http://localhost:9091/greeting/async              | A basic non-blocking call                                                                             |
| http://localhost:9091/greeting/sync               | A basic blocking call                                                                                 |
| http://localhost:9091/greeting/echo/8             | A basic call with parameters in path                                                                  |
| http://localhost:9091/swagger-ui.html             | The swagger UI, exposes all of the APIs                                                               |
| http://localhost:9091/actuator/activeProfiles     | Custom endpoint exposing just the active profiles, used to identify what environment we're running in |
| http://localhost:9091/actuator/health             | The health endpoint                                                                                   |
| http://localhost:9091/actuator/info               | The build info - including version, build time, etc                                                   |
| http://localhost:9094/actuator/metrics            | Captured metrics for the running instance                                                             |

## Source URLs

Links to the external documentation that was used as additional content to this codebase

| URL                                                                            | Description                                                                                                                                                                                                                                          |
| ------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| https://spring.io/guides/gs/spring-boot-docker/                                | The official spring-boot documentation on docker.  Recommends running the full spring-boot build process then cherry-picking the files, which is skipped in this sample to instead use the "copy-dependencies" maven plugin for an even quicker boot |
| https://jeremylong.github.io/DependencyCheck/dependency-check-maven/index.html | The maven plugin used to analyse the dependency vulnerabilities                                                                                                                                                                                      |
| https://maven.apache.org/maven-ci-friendly.html                                | How to pass versions numbers into maven without the versions plugin                                                                                                                                                                                  |
