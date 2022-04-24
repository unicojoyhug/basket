package io.unicojoyhug.basket.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
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
    private UUID itemId = UUID.randomUUID();

    @Test
    public void getById_returnsBasketItem(){
        Basket basket = new Basket(basketId, customerId, now);
        basketRepository.saveAndFlush(basket);

        int productNumber = 123;

        BasketItems basketItems = new BasketItems(itemId, productNumber, 3, now, now, basketId);
        BasketItems result = basketItemsRepository.saveAndFlush(basketItems);

        assertThat(basketItemsRepository.getById(result.getId()).getBasketId()).isEqualTo(basket.getId());
    }

}
