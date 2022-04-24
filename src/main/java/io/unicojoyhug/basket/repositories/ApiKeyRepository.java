package io.unicojoyhug.basket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    public Optional<ApiKey> findOneByKey(String key);
}