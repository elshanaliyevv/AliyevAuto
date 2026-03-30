FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy the gradle wrappers and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src src

# Build the application
RUN ./gradlew build -x test

# Final stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built jar
COPY --from=build /app/build/libs/*.jar app.jar

# Expose backend port
EXPOSE 9090

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
