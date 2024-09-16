# Use a Docker image that includes Docker CLI
FROM docker:latest AS docker-cli

# Remove the existing container if it exists
RUN docker rm -f swiftrails-dev || true

# Use Maven as the base image for building the application
FROM maven:3.6.3-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the project's POM file to the working directory
COPY pom.xml .

# Download dependencies specified in the POM file
RUN mvn dependency:go-offline

# Copy the rest of the application source code to the working directory
COPY src ./src

# Build the application
RUN mvn package

# Stage 2: Create a lightweight container with the JAR file
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the builder stage to the current directory
COPY --from=builder /app/target/SWIFTRAILS-0.0.1-SNAPSHOT.jar .

# Expose the port the application runs on
EXPOSE 8000

# Run the JAR file
CMD ["java", "-jar", "SWIFTRAILS-0.0.1-SNAPSHOT.jar"]
