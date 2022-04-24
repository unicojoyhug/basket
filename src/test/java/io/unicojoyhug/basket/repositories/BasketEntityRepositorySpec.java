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
public class BasketEntityRepositorySpec {
    @Autowired
    BasketEntityRepository basketEntityRepository;

    @Test
    public void getBasket_returnsBasket(){
        UUID customerId = UUID.randomUUID();
        UUID basketId = UUID.randomUUID();
        Instant created = Instant.now();

        BasketEntity basket = new BasketEntity(basketId, customerId, created);
        basketEntityRepository.saveAndFlush(basket);

        BasketEntity createdBasket = basketEntityRepository.getById(basket.getId());

        assertThat(createdBasket.getCreated()).isEqualTo(created);
    }

}
