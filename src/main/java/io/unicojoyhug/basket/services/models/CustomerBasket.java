package io.unicojoyhug.basket.services.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CustomerBasket {
    private UUID basketId;
    private UUID customerId;
    private Instant created;
    private Instant modified;
    private List<BasketItem> basketItems;

    public CustomerBasket(UUID basketId, UUID customerId, Instant created, Instant modified, List<BasketItem> basketItems) {
        this.basketId = basketId;
        this.customerId = customerId;
        this.created = created;
        this.modified = modified;
        this.basketItems = basketItems;
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

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }
}
