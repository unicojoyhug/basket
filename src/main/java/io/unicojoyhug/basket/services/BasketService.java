package io.unicojoyhug.basket.services;

import io.unicojoyhug.basket.services.models.CustomerBasket;

import java.util.Optional;
import java.util.UUID;

public interface BasketService {
    CustomerBasket getOrCreateBasket(Optional<UUID> basketId, UUID customerId);
    CustomerBasket updateBasket(UUID basketId, UUID customerId, int productNumber, int quantity);
    void deleteBasket(UUID basketId, UUID customerId);
}
