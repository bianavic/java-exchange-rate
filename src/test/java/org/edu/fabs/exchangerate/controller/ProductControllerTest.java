package org.edu.fabs.exchangerate.controller;

import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;
import org.edu.fabs.exchangerate.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Test")
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(productService.getAll()).thenReturn(Arrays.asList(
                new Product("Product 1", "Product description 1", 2, new BigDecimal("100.00"), CurrencySymbol.USD),
                new Product("Product 2", "Product description 2", 10, new BigDecimal("100.00"), CurrencySymbol.ARS)
        ));
        when(productService.getById(1L)).thenReturn(
                Optional.of(new Product("Product 1", "Product description 1", 2, new BigDecimal("100.00"), CurrencySymbol.USD))
        );
    }

    @Test
    @DisplayName("Verify if all products are successfully fetched")
    void shouldReturnAllProducts() throws Exception {

        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @DisplayName("Verify if one product is successfully fetched by it ID")
    void shouldReturnProductById() throws Exception {

        this.mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is("Product 1")))
                .andExpect(jsonPath("$.description", Matchers.is("Product description 1")))
                .andExpect(jsonPath("$.quantity", Matchers.is(2)))
                .andExpect(jsonPath("$.price", Matchers.is(100.00)))
                .andExpect(jsonPath("$.currency", Matchers.is("USD")));
    }

//    @Test
//    void addProduct() {
//    }
//
//    @Test
//    void updateProduct() {
//    }
//
//    @Test
//    void deleteProduct() {
//    }
//
//    @Test
//    void getTotalPrice() {
//    }

}