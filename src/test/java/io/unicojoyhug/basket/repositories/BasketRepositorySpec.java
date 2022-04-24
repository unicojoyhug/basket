package io.unicojoyhug.basket.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class BasketRepositorySpec {
    @Autowired
    BasketRepository basketRepository;
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
}
