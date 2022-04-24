package io.unicojoyhug.basket.services;

import io.unicojoyhug.basket.repositories.Basket;
import io.unicojoyhug.basket.repositories.BasketItems;
import io.unicojoyhug.basket.repositories.BasketItemsRepository;
import io.unicojoyhug.basket.repositories.BasketRepository;
import io.unicojoyhug.basket.services.models.BasketItem;
import io.unicojoyhug.basket.services.models.CustomerBasket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {
    BasketRepository basketRepository;
    BasketItemsRepository basketItemsRepository;
    PriceService priceService;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository, BasketItemsRepository basketItemsRepository, PriceService priceService) {

        this.basketRepository = basketRepository;
        this.basketItemsRepository = basketItemsRepository;
        this.priceService = priceService;
    }

    @Override
    public CustomerBasket getOrCreateBasket(Optional<UUID> basketId, UUID customerId) {
        Basket newBasket;
/**
 *         Baskets newBasket = basketId
 *                 .map(uuid -> basketsRepository.getById(uuid))
 *                 .orElseGet(() ->
 *                         basketsRepository.findByCustomerId(customerId).orElseGet(() ->
 *                                 basketsRepository.save(new Baskets(UUID.randomUUID(), customerId, Instant.now(), Instant.now()))));
 */
        if (basketId.isPresent()) {
            newBasket = basketRepository.getById(basketId.get());
        } else {
            newBasket = basketRepository.findByCustomerId(customerId)
                    .orElseGet(() -> basketRepository.save(new Basket(UUID.randomUUID(), customerId, Instant.now())));
        }

        List<BasketItem> items = getItems(newBasket.getId());
        Instant lastModified = getLastModified(newBasket.getCreated(), items);
        return new CustomerBasket(newBasket.getId(), customerId, newBasket.getCreated(),lastModified, items);
    }

    @Override
    public CustomerBasket updateBasket(UUID basketId, UUID customerId, int productNumber, int quantity) {
        Optional<BasketItems> item = basketItemsRepository.findByBasketIdAndProductNumber(basketId, productNumber);
        Instant now = Instant.now();
        Basket basket = basketRepository.getById(basketId);

        if(quantity == 0){
            basketItemsRepository.deleteByBasketIdAndProductNumber(basketId, productNumber);
        }else{
            item.map(
                    i ->
                            basketItemsRepository.save(new BasketItems(i.getId(), i.getProductNumber(), quantity, i.getCreated(), now, basketId))
            ).orElseGet(() ->basketItemsRepository.save(new BasketItems(UUID.randomUUID(), productNumber, quantity, now, now, basketId)));
        }

        List<BasketItem> items = getItems(basketId);

        return new CustomerBasket(basketId, customerId, basket.getCreated(),now, items);
    }

    @Override
    public void deleteBasket(UUID basketId, UUID customerId) {

    }


    private Instant getLastModified(Instant basketCreated, List<BasketItem> items){
        return  items.stream().map(BasketItem::getModified).max(Instant::compareTo).orElseGet(() -> basketCreated);
    }

    private List<BasketItem> getItems(UUID basketId) {
        return basketItemsRepository.findAllByBasketId(basketId)
                .stream()
                .map(item -> new BasketItem(item.getId(), item.getProductNumber(), item.getQuantity(), priceService.getPrice(item.getProductNumber()), item.getModified()))
                .collect(Collectors.toList());
    }

}
