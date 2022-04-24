package io.unicojoyhug.basket.controllers.models;

import java.util.UUID;

public class BasketUpdateRequest {

    private UUID basketId;
    private UUID customerId;
    private int quantity;
    private int productNumber;

    public BasketUpdateRequest(UUID basketId, UUID customerId, int quantity, int productNumber) {
        this.basketId = basketId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.productNumber = productNumber;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getBasketId() {
        return basketId;
    }

    public void setBasketId(UUID basketId) {
        this.basketId = basketId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }
}
