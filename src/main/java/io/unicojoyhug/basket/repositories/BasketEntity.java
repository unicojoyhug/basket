package io.unicojoyhug.basket.repositories;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "baskets")
public class BasketEntity {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "customer_id", nullable = false, updatable = false)
    private UUID customerId;

    @CreationTimestamp
    @Column(name = "created")
    private Instant created;

    public BasketEntity(){}

    public BasketEntity( UUID id, UUID customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public BasketEntity( UUID id, UUID customerId, Instant created) {
        this.id = id;
        this.customerId = customerId;
        this.created = created;
    }

    @Override
    public String toString() {
        return "Baskets{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", created=" + created +
                '}';
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }


    @Id
    public UUID getId() {
        return id;
    }

    public BasketEntity setId(UUID id) {
        this.id = id;
        return this;
    }
}
