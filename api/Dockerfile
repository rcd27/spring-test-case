FROM openjdk:8u252-slim
EXPOSE 8080

WORKDIR /api/
COPY build/libs/api-0.0.1-SNAPSHOT.jar api.jar

ENTRYPOINT ["java","-jar","api.jar"]

