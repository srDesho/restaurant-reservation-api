FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/restaurant-reservation_api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} restaurant-reservation_api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "restaurant-reservation-api.jar"]