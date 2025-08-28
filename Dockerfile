FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/obter-calendario-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 80
# Bind Spring Boot to Azure $PORT environment variable
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=${PORT}"]