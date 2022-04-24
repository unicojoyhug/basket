package io.unicojoyhug.basket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketEntityRepository extends JpaRepository<BasketEntity, UUID> {
}