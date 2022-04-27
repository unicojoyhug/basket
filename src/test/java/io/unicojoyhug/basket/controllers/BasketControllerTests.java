package io.unicojoyhug.basket.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import io.unicojoyhug.basket.controllers.models.BasketDeleteRequest;
import io.unicojoyhug.basket.controllers.models.BasketRequest;
import io.unicojoyhug.basket.controllers.models.BasketUpdateRequest;
import io.unicojoyhug.basket.services.BasketService;
import io.unicojoyhug.basket.services.models.CustomerBasket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BasketService basketService;

    private CustomerBasket basket;
    private ObjectMapper mapper;

    private String xApiKey = "x-api-key";

    private UUID customerId = UUID.fromString("82c88862-1771-444e-8695-50a577d8d611");
    @Before
    public void setUp(){
        this.basket = basketService.getOrCreateBasket(Optional.empty(), customerId);
        this.mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
    }

    @Test
    public void getOrCreateCustomerBasket() throws  Exception {
        BasketRequest basketRequest= new BasketRequest(customerId, Optional.empty());
        String jsonString = mapper.writeValueAsString(basketRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/basket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .header(xApiKey,"0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void getOrCreateCustomerBasket_unknownBasket() throws  Exception {
        BasketRequest basketRequest= new BasketRequest(customerId, Optional.of(UUID.fromString("82c88862-1771-444e-8695-50a577d8d611")));
        String jsonString = mapper.writeValueAsString(basketRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/basket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString).header(xApiKey,"0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getOrCreateCustomerBasket_FirstTimeBasket() throws  Exception {
        BasketRequest basketRequest= new BasketRequest(UUID.randomUUID(), Optional.empty());
        String jsonString = mapper.writeValueAsString(basketRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/basket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString).header(xApiKey,"0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBasket_existing_basket() throws  Exception {
        BasketUpdateRequest basketUpdateRequest = new BasketUpdateRequest(basket.getBasketId(), basket.getCustomerId(), 20, 222);
        String jsonString = mapper.writeValueAsString(basketUpdateRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/basket/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString).header(xApiKey,"0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBasket_withoutBasketId_isBadRequest() throws  Exception {
        String jsonString="{\"customerId\": \"82c88862-1771-444e-8695-50a577d8d000\", \"basketId\": null} ";
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/basket/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString).header(xApiKey,"0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBasket() throws  Exception {
        BasketDeleteRequest basketDeleteRequest = new BasketDeleteRequest(basket.getBasketId(), basket.getCustomerId());
        String jsonString = mapper.writeValueAsString(basketDeleteRequest);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/basket/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString).header(xApiKey,"0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
