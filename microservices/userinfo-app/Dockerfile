FROM openjdk:11.0.1-jre-slim

EXPOSE 8084
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]