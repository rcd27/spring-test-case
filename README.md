### Build project:
`./gradlew cleanRebuild`

### Run docker containers:
`docker-compose up -d`

### Check if it works:
Registration request:
`curl --location --request POST 'http://localhost:8080/api/v1/verification/verify' \
 --header 'Content-Type: application/json' \
 --data-raw '{
 "firstName": "Stanislav",
 "lastName": "Zemlyakov",
 "email": "redtom@yandex.ru",
 "dateOfBirth": "01.08.1989",
 "habitatCity": "Saint-Petersburg",
 "registrationCity":"s"
 }'`
 The result should be an - `id`.

### Stop docker containers and remove `docker images`:
`./dockerRemoveAll.bash`
 
# About

A project with microservice architecture. `api` accepts a `RegistrationRequest`, which gains `UUID` by `idgenerator`(used for checking the `RegistrationRequest` status). The request is then approved/verified by `approver` (mocked for now). Approved request trigger `mailer` to send email to a requested adress (mocked as well). 

### Tech stack:

- Kotlin
- Spring Boot
- WebFlux
- MongoDB
- Kafka
- MockK
- Truth
- Wiremock
- Spring Cloud Contract

#### Additional info:
Project uses `spring-cloud-contract` to be sure microservices follow the contract and work properly. For now, it is implemented for `api`(Consumer) and `idgenerator`(Producer). To generate stubs and publish them to a local Maven repository, use:
```
./gradlew clean idgenerator:build idgenerator:publishToMavenLocal -x test
```
