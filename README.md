# Basket Microservice

## Case : create a shopping cart / basket microservice.

### Requirements
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