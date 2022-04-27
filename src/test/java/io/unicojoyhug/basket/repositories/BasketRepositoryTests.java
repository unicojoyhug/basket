package io.unicojoyhug.basket.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BasketRepositoryTests {
    @Autowired
    BasketRepository basketRepository;
    @Autowired
    private BasketItemsRepository basketItemsRepository;

    private UUID customerId = UUID.randomUUID();
    private UUID basketId = UUID.randomUUID();
    private Instant created = Instant.now();

    @Test
    public void getBasket_returnsBasket(){
        Basket basket = new Basket(basketId, customerId, created);
        basketRepository.saveAndFlush(basket);

        Basket createdBasket = basketRepository.getById(basket.getId());

        assertThat(createdBasket.getCreated()).isEqualTo(created);
    }

    @Test
    public void findByCustomerId_returnsBasket(){
        Basket basket = new Basket(basketId, customerId, created);
        basketRepository.save(basket);
        basketRepository.flush();

        Optional<Basket> createdBasket = basketRepository.findByCustomerId(basket.getCustomerId());

        assertThat(createdBasket.isPresent()).isTrue();
    }

    @Test
    public void noCustomerBasketFound_returnOptionalEmpty(){
        UUID customerId = UUID.randomUUID();

        Optional<Basket> createdBasket = basketRepository.findByCustomerId(customerId);
        assertThat(createdBasket.isPresent()).isFalse();
    }

    @Test(expected = JpaObjectRetrievalFailureException.class)
    public void deleteById(){
        UUID anotherBasketId = UUID.randomUUID();
        UUID anotherCustomerId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        UUID itemId2 = UUID.randomUUID();
        UUID itemId3 = UUID.randomUUID();
        UUID itemId4 = UUID.randomUUID();

        Basket basket = new Basket(basketId, customerId, created);
        basketRepository.saveAndFlush(basket);

        Basket anotherBasket = new Basket(anotherBasketId, anotherCustomerId, created);
        basketRepository.saveAndFlush(anotherBasket);

        int productNumber = 111;

        BasketItems basketItems1 = new BasketItems(itemId, productNumber, 1, created, created, basketId);
        BasketItems basketItems2 = new BasketItems(itemId2, 222, 2, created, created, basketId);
        BasketItems basketItems3 = new BasketItems(itemId3, 333, 3, created, created, anotherBasketId);
        BasketItems basketItems4 = new BasketItems(itemId4, 444, 4, created, created, basketId);

        List<BasketItems> items = List.of(basketItems1, basketItems2, basketItems3, basketItems4);

        Basket createdBasket = basketRepository.getById(basket.getId());

        assertThat(createdBasket.getCreated()).isEqualTo(created);

        List<BasketItems> itemsToDelete = basketItemsRepository.findAllByBasketId(basketId);
        itemsToDelete.parallelStream().forEach(item -> basketItemsRepository.deleteById(item.getId()));

        List<BasketItems> shouldBeEmptyList = basketItemsRepository.findAllByBasketId(basketId);

        assertThat(shouldBeEmptyList.size()).isEqualTo(0);

        basketRepository.deleteById(basketId);
        basketRepository.getById(basketId);
    }
}
