package io.unicojoyhug.basket.controllers;

import io.unicojoyhug.basket.controllers.models.BasketRequest;
import io.unicojoyhug.basket.services.BasketService;
import io.unicojoyhug.basket.services.models.CustomerBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/basket")
public class BasketController {
    BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping
    public CustomerBasket getOrCreateCustomerBasket(@RequestBody BasketRequest request) {
        return basketService.getOrCreateBasket(request.getBasketId(), request.getCustomerId());
    }
}
