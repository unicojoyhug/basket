# Basket Microservice

## Case : create a shopping cart / basket microservice.

### Requirements
- docker for DB
```shell
docker-compose up -d
```
- api key to insert in DB like below:
```shell
insert into api_key (key) values ('0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a');
```

### Objectives
- Basic CRUD
    - Add to basket
    - Remove from the basket
    - List basket
    - Change quantity
- DB
  - Basket
    - id(UUID), customerId(UUID), created
  - BasketItems
    - id(UUID), basketId(UUID - fk), created, modified, productNumber, quantity
  - ApiKey
    - id, key

### Stack
- Spring Boot - data, security, web
- Java 18
- Postgres DB, hibernate, liquibase