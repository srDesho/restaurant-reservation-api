FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/restaurant-reservation-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} restaurant-reservation-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "restaurant-reservation-api.jar"]