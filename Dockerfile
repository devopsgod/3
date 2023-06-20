# Use the official gradle image to create a build artifact.
# https://hub.docker.com/_/gradle
FROM gradle:7.0.0-jdk11 as builder

# Copy local code to the container image.
WORKDIR /app
COPY build.gradle .
COPY src ./src

# Build a release artifact.
RUN gradle clean build --no-daemon

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK (not Alpine) because of https://github.com/docker-library/openjdk/issues/211
FROM adoptopenjdk:11-jre-hotspot

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/build/libs/*.jar /app.jar

# Run the web service on container startup. 
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
