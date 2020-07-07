FROM openjdk:8u252-slim
EXPOSE 8081

WORKDIR /idgenerator/
COPY build/libs/idgenerator-0.0.1-SNAPSHOT.jar idgenerator.jar

ENTRYPOINT ["java","-jar","idgenerator.jar"]

