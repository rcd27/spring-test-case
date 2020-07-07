```
docker build --tag spring-test-case-api ./api
docker run -p 8080:8080 --name api spring-test-case-api

docker build --tag spring-test-case-idgenerator ./idgenerator
docker run -p 8081:8081 --name idgenerator spring-test-case-idgenerator
```