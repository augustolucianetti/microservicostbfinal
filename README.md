# microservicostbfinal

## Trabalho final da disciplina MICROSERVICES DEVELOPMENT & API

## Swagger
```
http://localhost:8080/swagger-ui.html
```
## Rest

### /transactions
```
curl -X POST "http://localhost:8080/transactions?amount=200&timestamp=15563637324810" -H "accept: application/json"
```

### /statistics
```
curl -X GET "http://localhost:8080/statistics" -H "accept: application/json"
```

## Run
```
docker run -p 8080:8080 springio/microservicostbfinal
```
