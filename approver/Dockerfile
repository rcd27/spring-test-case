FROM openjdk:8u252-slim
EXPOSE 8082

WORKDIR /approver/
COPY build/libs/approver-0.0.1-SNAPSHOT.jar approver.jar

ENTRYPOINT ["java","-jar","approver.jar"]

