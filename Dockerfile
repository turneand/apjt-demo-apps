FROM openjdk:8-jre-alpine
WORKDIR /app
EXPOSE 8080

# Add a runtime user account, to avoid running as "root"
RUN adduser -D app-user

# Copy the dependencies first, as these are much less likely to change
COPY target/dependency               /app/lib
COPY target/apjt-dropwizard-demo.jar /app

# Switch user to our runtime account, ensuring we never run as "root"
USER app-user

# The "java.security.egd" entry massively speeds up the boot times
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-cp", "/app/apjt-dropwizard-demo.jar:/app/lib/*", "com.apjt.DropWizardDemoApplication", "server", "config.yml"]
