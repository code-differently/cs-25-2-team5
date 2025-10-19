# Stage 1: Build the application
FROM gradle:jdk17-jammy AS build

WORKDIR /app

# Copy only the build files first to leverage Docker's layer caching
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the application
RUN gradle build -x test --no-daemon

# Stage 2: Create the final runtime image
FROM eclipse-temurin:17-jre


WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port your application listens on (e.g., 8080 for Spring Boot)
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]