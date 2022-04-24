package io.unicojoyhug.basket.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BasketItemsRepositorySpec {
    @Autowired
    private BasketItemsRepository basketItemsRepository;
    @Autowired
    private BasketRepository basketRepository;

    private Instant now = Instant.now();
    private UUID customerId = UUID.randomUUID();
    private UUID basketId = UUID.randomUUID();
    private UUID anotherBasketId = UUID.randomUUID();
    private UUID anotherCustomerId = UUID.randomUUID();
    private UUID itemId = UUID.randomUUID();
    private UUID itemId2 = UUID.randomUUID();
    private UUID itemId3 = UUID.randomUUID();
    private UUID itemId4 = UUID.randomUUID();

    @Test
    public void getById_returnsBasketItem(){
        Basket basket = new Basket(basketId, customerId, now);
        basketRepository.saveAndFlush(basket);

        int productNumber = 123;

        BasketItems basketItems = new BasketItems(itemId, productNumber, 3, now, now, basketId);
        BasketItems result = basketItemsRepository.saveAndFlush(basketItems);

        assertThat(basketItemsRepository.getById(result.getId()).getBasketId()).isEqualTo(basket.getId());
    }

    @Test
    public void deleteByBasketIdAndProductNumber_deleteMatchingItem(){
        Basket basket = new Basket(basketId, customerId, now);
        basketRepository.saveAndFlush(basket);

        Basket anotherBasket = new Basket(anotherBasketId, anotherCustomerId, now);
        basketRepository.saveAndFlush(anotherBasket);

        int productNumber = 111;

        BasketItems basketItems1 = new BasketItems(itemId, productNumber, 1, now, now, basketId);
        BasketItems basketItems2 = new BasketItems(itemId2, 222, 2, now, now, basketId);
        BasketItems basketItems3 = new BasketItems(itemId3, 333, 3, now, now, anotherBasketId);
        BasketItems basketItems4 = new BasketItems(itemId4, 444, 4, now, now, basketId);

        List<BasketItems> items = List.of(basketItems1, basketItems2, basketItems3, basketItems4);

        List<BasketItems> result = basketItemsRepository.saveAllAndFlush(items);
        assertThat(basketItemsRepository.findAllByBasketId(basketId).size()).isEqualTo(3);

        basketItemsRepository.deleteByBasketIdAndProductNumber(basketId, productNumber);
        assertThat(basketItemsRepository.findAllByBasketId(basketId).size()).isEqualTo(2);
    }

}
