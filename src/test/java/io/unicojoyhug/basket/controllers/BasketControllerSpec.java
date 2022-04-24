package io.unicojoyhug.basket.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {BasketController.class})
@WebMvcTest
public class BasketControllerSpec {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void getOrCreate(){
//        ObjectBean objectBean = new ObjectBean();
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/basket")
//                .header("Key", "0ecd5ba3-d059-43bf-b0f0-1cf9c6a3e36a" )
//                        .content("{ "accountType": "SAVINGS", "balance": 5000.0 }")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//
//
//
//    }
}
