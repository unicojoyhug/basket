package io.unicojoyhug.basket.services.models;

import java.time.Instant;
import java.util.UUID;

public class BasketItem {
    private UUID id;
    private int productNumber;
    private int quantity;
    private int unitPrice;
    private Instant modified;

    public BasketItem() {}

    public BasketItem(UUID id, int productNumber, int quantity, int unitPrice, Instant modified) {
        this.id = id;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.modified = modified;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}
