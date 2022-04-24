package io.unicojoyhug.basket.services;

import org.springframework.stereotype.Service;

/**
 * Mock for PriceService - Assuming it will be from a price microservice.
 */

@Service
public class PriceServiceImpl implements PriceService {
    @Override
    public int getPrice(int productNumber) {
        return 100;
    }
}
