package io.unicojoyhug.basket.controllers.models;

import java.util.Optional;
import java.util.UUID;

public class BasketRequest {
    private UUID customerId;
    private Optional<UUID> basketId;

    public BasketRequest(UUID customerId, Optional<UUID> basketId) {
        this.customerId = customerId;
        this.basketId = basketId;
    }

    @Override
    public String toString() {
        return "BasketRequest{" +
                "customerId=" + customerId +
                ", basketId=" + basketId +
                '}';
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Optional<UUID> getBasketId() {
        return basketId;
    }

    public void setBasketId(Optional<UUID> basketId) {
        this.basketId = basketId;
    }
}
