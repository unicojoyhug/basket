package io.unicojoyhug.basket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BasketItemsRepository extends JpaRepository<BasketItems, UUID> {
    List<BasketItems> findAllByBasketId(UUID basketId);
    Optional<BasketItems> findByBasketIdAndProductNumber(UUID basketId, int productNumber);
    void deleteByBasketIdAndProductNumber(UUID basketId, int productNumber);
}