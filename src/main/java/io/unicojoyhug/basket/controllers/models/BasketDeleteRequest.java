package io.unicojoyhug.basket.controllers.models;

import java.util.UUID;

public class BasketDeleteRequest {
    private UUID basketId;
    private UUID customerId;

    public BasketDeleteRequest(UUID basketId, UUID customerId) {
        this.basketId = basketId;
        this.customerId = customerId;
    }

    public UUID getBasketId() {
        return basketId;
    }

    public void setBasketId(UUID basketId) {
        this.basketId = basketId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}
