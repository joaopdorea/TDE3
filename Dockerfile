# Stage 1: Build the application
FROM openjdk:17 AS build
LABEL authors="joaodorea"
LABEL description="This is the Dockerfile for the Users service"
# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Copy the source code
COPY src src

# Stage 2: Run the application
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/users-0.0.1-SNAPSHOT.jar users.jar

# Expose port 8081
EXPOSE 8081

# Define the entrypoint
ENTRYPOINT ["java", "-jar", "users.jar"]