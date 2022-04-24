package io.unicojoyhug.basket.services;

import io.unicojoyhug.basket.repositories.Basket;
import io.unicojoyhug.basket.repositories.BasketItems;
import io.unicojoyhug.basket.repositories.BasketItemsRepository;
import io.unicojoyhug.basket.repositories.BasketRepository;
import io.unicojoyhug.basket.services.models.BasketItem;
import io.unicojoyhug.basket.services.models.CustomerBasket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.List;
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
    @Test
    public void getOrCreateCustomerBasket_withoutBasketId_customerNotFound_triggerSave_returnsCustomerBasket() {
        Basket basket = new Basket(basketId, customerId);

        when(basketRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());
        when(basketRepository.save(new Basket(Mockito.any(), customerId))).thenReturn(basket);

        CustomerBasket result = basketService.getOrCreateBasket(Optional.empty(), customerId);

        assertThat(result.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    public void getOrCreateCustomerBasket_withBasketId_triggerGeById_returnsCustomerBasket() {
        Basket basket = new Basket(basketId, customerId);

        when(basketRepository.getById(basketId)).thenReturn(basket);

        CustomerBasket result = basketService.getOrCreateBasket(Optional.of(basketId), customerId);

        assertThat(result.getBasketId()).isEqualTo(basketId);
    }

    @Test
    public void getOrCreateCustomerBasket_withBasketIdAndItems_returnsCustomerBasketWithItems() {
        Basket basket = new Basket(basketId, customerId);
        Instant now = Instant.now();
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        int productNumber = 123;
        BasketItems basketItems1 = new BasketItems(id1, productNumber, 1, now, now, basketId);
        BasketItems basketItems2 = new BasketItems(id2, 111, 2, now, now, basketId);
        BasketItems basketItems3 = new BasketItems(id3, 222, 3, now, now, basketId);
        List<BasketItems> items = List.of(basketItems1, basketItems2, basketItems3);

        when(basketRepository.getById(basketId)).thenReturn(basket);
        when(basketItemsRepository.findAllByBasketId(basketId)).thenReturn(items);
        when(priceService.getPrice(Mockito.anyInt())).thenReturn(100);

        CustomerBasket result = basketService.getOrCreateBasket(Optional.of(basketId), customerId);
        List<BasketItem> itemResult = result.getBasketItems();

        assertThat(result.getBasketId()).isEqualTo(basketId);
        assertThat(itemResult.size()).isEqualTo(3);
        itemResult.forEach(i -> assertThat(i.getUnitPrice()).isEqualTo((100)));
    }

    @Test
    public void updateBasket_returnsCustomerBasketWithItems() {
        UUID customerId = UUID.randomUUID();
        UUID basketId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        Basket basket = new Basket(basketId, customerId);
        Instant now = Instant.now();
        int productNumber = 123;
        BasketItems basketItems1 = new BasketItems(itemId, productNumber, 1, now, now, basketId);
        BasketItems basketItems1Updated = new BasketItems(itemId, productNumber, 5, now, now, basketId);

        List<BasketItems> items = List.of(basketItems1);

        when(basketRepository.getById(basketId)).thenReturn(basket);
        when(basketItemsRepository.findByBasketIdAndProductNumber(basketId, productNumber)).thenReturn(Optional.of(basketItems1));
        when(priceService.getPrice(productNumber)).thenReturn(100);
        when(basketItemsRepository.save(Mockito.any())).thenReturn(new BasketItems(itemId, productNumber, 5, now, now, basketId));
        when(basketItemsRepository.findAllByBasketId(basketId)).thenReturn(List.of(basketItems1Updated));

        BasketItem item = new BasketItem(itemId, productNumber, 3, 100, now);

        CustomerBasket result = basketService.updateBasket(basketId, customerId, productNumber, 5);
        List<BasketItem> itemResult = result.getBasketItems();

        assertThat(result.getBasketId()).isEqualTo(basketId);
        assertThat(itemResult.size()).isEqualTo(1);
        itemResult.forEach(i -> assertThat(i.getQuantity()).isEqualTo((5)));
    }

    @Test
    public void deleteBasket() {
        Basket basket = new Basket(basketId, customerId);
        Instant now = Instant.now();
        int productNumber = 123;
        BasketItems basketItems1 = new BasketItems(itemId, productNumber, 1, now, now, basketId);
        BasketItems basketItems1Updated = new BasketItems(itemId, productNumber, 5, now, now, basketId);

        List<BasketItems> items = List.of(basketItems1);

        when(basketItemsRepository.findAllByBasketId(basketId)).thenReturn(List.of(basketItems1Updated));

        BasketItem item = new BasketItem(itemId, productNumber, 3, 100, now);

        basketService.deleteBasket(basketId, customerId);
    }
}
