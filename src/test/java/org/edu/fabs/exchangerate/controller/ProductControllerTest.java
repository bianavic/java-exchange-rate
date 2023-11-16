package org.edu.fabs.exchangerate.controller;

import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;
import org.edu.fabs.exchangerate.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    final List<Product> products = Arrays.asList(
            Product.builder()
                    .name("Product 1")
                    .description("Product description 1")
                    .quantity(2)
                    .price(new BigDecimal("100.00"))
                    .currency(CurrencySymbol.USD)
                    .build(),
            Product.builder()
                    .name("Product 2")
                    .description("Product description 2")
                    .quantity(10)
                    .price(new BigDecimal("100.00"))
                    .currency(CurrencySymbol.ARS)
                    .build()
    );

    @BeforeEach
    void setUp() {
        Mockito.when(productService.getAll()).thenReturn(products);
        Mockito.when(productService.getById(1L)).thenReturn(Optional.of(products.get(0)));
    }

    @Test
    @DisplayName("Should successfully return all products")
    void shouldFetchAllProducts() throws Exception {

        this.mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    @DisplayName("Should successfully return one product by its ID")
    void shouldFetchProductById() throws Exception {

        this.mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is("Product 1")))
                .andExpect(jsonPath("$.description", Matchers.is("Product description 1")))
                .andExpect(jsonPath("$.quantity", Matchers.is(2)))
                .andExpect(jsonPath("$.price", Matchers.is(100.00)))
                .andExpect(jsonPath("$.currency", Matchers.is("USD")));
    }

    @Test
    @DisplayName("Should successfully add a product and return created status and product data")
    void shouldAddProductAndReturnCreatedStatusAndProductData() throws Exception {

        Product product = new Product("New Product", "New Product Description", 5, new BigDecimal("50.00"), CurrencySymbol.USD);
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        String requestBody = "{\"name\": \"New Product\", \"description\": \"New Product Description\", \"quantity\": 5, \"price\": 50.00, \"currency\": \"USD\"}";

        this.mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.description").value("New Product Description"))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.price").value(50.00))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

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