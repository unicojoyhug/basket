package io.unicojoyhug.basket.services;

import io.unicojoyhug.basket.repositories.Basket;
import io.unicojoyhug.basket.repositories.BasketItemsRepository;
import io.unicojoyhug.basket.repositories.BasketRepository;
import io.unicojoyhug.basket.services.models.CustomerBasket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketServiceSpec {
    @Mock
    private BasketRepository basketRepository;
    @Mock
    private BasketItemsRepository basketItemsRepository;
    @Mock
    private PriceService priceService;

    BasketService basketService;

    private UUID customerId = UUID.randomUUID();
    private UUID basketId = UUID.randomUUID();
    private UUID itemId = UUID.randomUUID();


    @Before
    public void setUp() {
        basketService = new BasketServiceImpl(basketRepository, basketItemsRepository, priceService);
    }

    @Test
    public void getOrCreateCustomerBasket_withoutBasketId_customerFound_returnsCustomerBasket() {

        Basket basket = new Basket(basketId, customerId);

        when(basketRepository.findByCustomerId(customerId)).thenReturn(Optional.of(basket));

        CustomerBasket result = basketService.getOrCreateBasket(Optional.empty(), customerId);

        assertThat(result.getCustomerId()).isEqualTo(customerId);
    }



}
