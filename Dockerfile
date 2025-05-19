# Use an official OpenJDK 23 runtime as a parent image
FROM openjdk:23-jdk-slim

RUN apt-get update && apt-get install -y curl

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/coop-steamer-service-0.0.1-SNAPSHOT.jar app.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port your Spring Boot app will run on
EXPOSE 8080
