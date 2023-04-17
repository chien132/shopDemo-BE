FROM openjdk:8-jdk-alpine

LABEL maintainer="chien132"

WORKDIR /app

COPY build/libs/shopdemo-0.0.1-SNAPSHOT.jar /app/shopdemo.jar

ENTRYPOINT ["java", "-jar", "shopdemo.jar"]

EXPOSE 8080
