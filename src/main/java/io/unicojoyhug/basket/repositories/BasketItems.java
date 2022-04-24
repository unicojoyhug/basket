package io.unicojoyhug.basket.repositories;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "basket_items")
public class BasketItems {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "product_number", nullable = false, updatable = false)
    private int productNumber;

    @Column(name = "quantity")
    @PositiveOrZero
    private int quantity;

    @CreationTimestamp
    @Column(name = "created")
    private Instant created;

    @UpdateTimestamp
    @Column(name ="modified")
    private Instant modified;

    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    @Cascade({CascadeType.DELETE})
    private UUID basketId;

    public BasketItems() {}

    public BasketItems(UUID id, int productNumber, int quantity, Instant created, Instant modified, UUID basketId) {
        this.id = id;
        this.productNumber = productNumber;
        this.quantity = quantity;
        this.created = created;
        this.modified = modified;
        this.basketId = basketId;
    }

    @Override
    public String toString() {
        return "BasketItems{" +
                "id=" + id +
                ", productNumber=" + productNumber +
                ", quantity=" + quantity +
                ", created=" + created +
                ", modified=" + modified +
                ", basketId=" + basketId +
                '}';
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
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

    @Id
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
