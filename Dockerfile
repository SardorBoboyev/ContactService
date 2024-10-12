FROM openjdk:17-jdk-alpine

LABEL authors="Sardor_Boboyev1"

EXPOSE 8095

COPY build/libs/ContactService-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]