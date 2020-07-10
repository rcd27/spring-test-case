FROM openjdk:8u252-slim
EXPOSE 8083

WORKDIR /mailer/
COPY build/libs/mailer-0.0.1-SNAPSHOT.jar mailer.jar

ENTRYPOINT ["java","-jar","mailer.jar"]

