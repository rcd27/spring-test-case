### Сборка проекта:
`./gradlew cleanRebuild`

### Запуск контейнеров:
`docker-compose up -d`

### Проверка работы:
Запрос на регистрацию:
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
 Результат запроса - `id`.

### Остановить все контейнеры и удалить `docker images`:
`./dockerRemoveAll.bash`
 
# Описание

Проект с микросервисной архитектурой. `api` принимает `http` запросы на регистрацию пользователя. Далее каждому запросу присваивается `UUID` для идентификации запроса между сервисами, а также проверки его статуса - происходит в сервисе `idgenerator`. Затем отправляется на верификацию/подтверждение в `approver` (на данный момент логика подтверждения замокана). Между `approver` и `mailer` настроен брокер сообщений `Kafka`. В случае подтверждения регистрации в `approver`, соответствующее сообщение отправляется в `mailer` для дальнейшей отправки почты на `email` пользователя. 

### Использованные технологии:

- Kotlin
- Spring Boot
- WebFlux
- MongoDB
- Kafka
- MockK
- Truth
