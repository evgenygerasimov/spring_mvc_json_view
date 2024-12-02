FROM openjdk:18-jdk-alpine
WORKDIR /app
COPY target/spring_mvc_json_view-1.0.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080