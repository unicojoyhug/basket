package io.unicojoyhug.basket.services;

/**
 * Mock for PriceService - Assuming it will be from a price microservice.
 */

public class PriceServiceImpl implements PriceService {
    @Override
    public int getPrice(int productNumber) {
        return 100;
    }
}
