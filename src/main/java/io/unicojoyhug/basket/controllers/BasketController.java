package io.unicojoyhug.basket.controllers;

import io.unicojoyhug.basket.controllers.models.BasketDeleteRequest;
import io.unicojoyhug.basket.controllers.models.BasketRequest;
import io.unicojoyhug.basket.controllers.models.BasketUpdateRequest;
import io.unicojoyhug.basket.errors.NegativeQuantityException;
import io.unicojoyhug.basket.services.BasketService;
import io.unicojoyhug.basket.services.models.CustomerBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/api/basket")
public class BasketController {
    BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public CustomerBasket getOrCreateCustomerBasket(@RequestBody BasketRequest request) {
        return basketService.getOrCreateBasket(request.getBasketId(), request.getCustomerId());
    }

    @PostMapping(path = "/update")
    public CustomerBasket updateBasket(@RequestBody BasketUpdateRequest request){
        if(request.getQuantity()<0) throw new NegativeQuantityException("Quantity cannot be negative.");
        return basketService.updateBasket(request.getBasketId(),request.getCustomerId(), request.getProductNumber(), request.getQuantity());
    }

    @DeleteMapping(path = "/delete")
    public void deleteBasket(@RequestBody BasketDeleteRequest request){
        basketService.deleteBasket(request.getBasketId(), request.getCustomerId());
    }
}
