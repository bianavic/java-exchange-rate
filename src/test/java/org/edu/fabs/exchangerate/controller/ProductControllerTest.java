package org.edu.fabs.exchangerate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.edu.fabs.exchangerate.dto.ProductUpdateDTO;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;
import org.edu.fabs.exchangerate.repository.ProductRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("Product Controller Test")
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    final List<Product> products = Arrays.asList(
            Product.builder()
                    .id(1L)
                    .name("Product 1")
                    .description("Product description 1")
                    .quantity(2)
                    .price(new BigDecimal("100.00"))
                    .currency(CurrencySymbol.USD)
                    .build(),
            Product.builder()
                    .id(2L)
                    .name("Product 2")
                    .description("Product description 2")
                    .quantity(10)
                    .price(new BigDecimal("100.00"))
                    .currency(CurrencySymbol.ARS)
                    .build()
    );

    Product updatedProduct = Product.builder()
            .id(1L)
            .name("Product 1")
            .description("Product description 1")
            .quantity(5)
            .price(new BigDecimal(150.00))
            .currency(CurrencySymbol.EUR)
            .build();

    @BeforeEach
    void setUp() {
        Mockito.when(productService.getAll()).thenReturn(products);
        Mockito.when(productService.getById(1L)).thenReturn(Optional.of(products.get(0)));
        when(productService.updateProduct(any(Long.class), any(Product.class))).thenReturn(updatedProduct);
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

    @Test
    @DisplayName("Should successfully update product information by ID")
    void shouldUpdateProduct() throws Exception {
        Product productToUpdate = products.get(0);
        ProductUpdateDTO updatedProductDTO = new ProductUpdateDTO(5, new BigDecimal(150.00), CurrencySymbol.EUR);

        this.mockMvc.perform(put("/products/{id}", productToUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(updatedProductDTO.quantity()))
                .andExpect(jsonPath("$.price").value(updatedProductDTO.price()))
                .andExpect(jsonPath("$.currency").value(updatedProduct.getCurrency().toString()));
    }

    @Test
    @DisplayName("Should successfully delete one product by its ID")
    void shouldDeleteProductById() throws Exception {
        Product productToDelete = products.get(0);
        List<Product> updatedProductList = new ArrayList<>(products);
        updatedProductList.remove(productToDelete);
        Mockito.when(productService.getAll()).thenReturn(updatedProductList);

        this.mockMvc.perform(delete("/products/{id}", productToDelete.getId()))
                .andExpect(status().isOk());

        List<Product> actualUpdatedProductList = (List<Product>) productService.getAll();
        assertThat(actualUpdatedProductList).hasSize(1);
        assertThat(actualUpdatedProductList.get(0).getId()).isEqualTo(2L);
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}


//
//    @Test
//    void getTotalPrice() {
//    }