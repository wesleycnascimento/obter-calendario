# Use Java 17 JDK base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy built JAR from Maven
COPY target/obter-calendario-0.0.1-SNAPSHOT.jar app.jar

# Run the app
ENTRYPOINT ["java","-jar","/app/app.jar"]

# Expose the default Spring Boot port
EXPOSE 8080