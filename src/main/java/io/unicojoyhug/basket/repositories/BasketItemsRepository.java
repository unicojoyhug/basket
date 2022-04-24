package io.unicojoyhug.basket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BasketItemsRepository extends JpaRepository<BasketItems, UUID> {
    List<BasketItems> findAllByBasketId(UUID basketId);
}