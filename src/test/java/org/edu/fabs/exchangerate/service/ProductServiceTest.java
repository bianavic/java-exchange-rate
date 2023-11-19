package org.edu.fabs.exchangerate.service;

import com.google.gson.Gson;
import feign.FeignException;
import org.edu.fabs.exchangerate.dto.ExchangeRateResponse;
import org.edu.fabs.exchangerate.dto.ProductUpdateDTO;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.InvalidCurrencyCodeException;
import org.edu.fabs.exchangerate.handler.ResourceNotFoundException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;
import org.edu.fabs.exchangerate.repository.ProductRepository;
import org.edu.fabs.exchangerate.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Product Service Tests")
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ExchangeFeignClient exchangeFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Description("Should successfully retrieve all products")
    public void shouldGetAllProducts() {

        List<Product> products = new ArrayList<>();
        products.add(new Product("Product 1", "Product description 1", 2, new BigDecimal(100.00), CurrencySymbol.USD));
        products.add(new Product("Product 2", "Product description 2", 10, new BigDecimal(100.00), CurrencySymbol.ARS));

        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<Product> result = (List<Product>) productService.getAll();

        assertThat(result).isEqualTo(products);
        // verify if repository method is called at least 1 time
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @Description("Should successfully retrieve a product by its ID")
    public void shouldGetProductById() {

        Product product = new Product(1L, "Product 1", "Product description 1", 2, new BigDecimal(100.00), CurrencySymbol.USD);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getById(1L);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(product);
        // verify if repository method is called at least 1 time
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @Description("Should throw ResourceNotFoundException when product with ID does not exist")
    public void shouldThrowNotFoundExceptionForNonexistentProduct() {

        Long productId = 1L;
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Resource ID not found: " + productId)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);

        // verify if repository method is called at least 1 time
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @Description("Should successfully add a new product")
    public void shouldAddNewProduct() {

        Product product = new Product(1L, "Product 1", "Product description 1", 2, new BigDecimal(100.00), CurrencySymbol.USD);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product);

        assertThat(result)
                .isEqualTo(product)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("name", "Product 1")
                .hasFieldOrPropertyWithValue("description", "Product description 1")
                .hasFieldOrPropertyWithValue("quantity", 2)
                .hasFieldOrPropertyWithValue("price", new BigDecimal(100))
                .hasFieldOrPropertyWithValue("currency", CurrencySymbol.USD);
    }

    @Test
    @DisplayName("Should successfully update product information by ID")
    public void shouldUpdateProduct() {
        Long productId = 1L;
        Product product = new Product(productId, "Product 1", "Product description 1", 2, new BigDecimal(100.00), CurrencySymbol.USD);
        ProductUpdateDTO productToUpdateDTO = new ProductUpdateDTO(3, new BigDecimal(150.00), CurrencySymbol.EUR);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.updateProduct(productId, productToUpdateDTO.toModel());

        assertThat(updatedProduct)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", productId)
                .hasFieldOrPropertyWithValue("name", "Product 1")
                .hasFieldOrPropertyWithValue("description", "Product description 1")
                .hasFieldOrPropertyWithValue("quantity", 3)
                .hasFieldOrPropertyWithValue("price", new BigDecimal(150.00))
                .hasFieldOrPropertyWithValue("currency", CurrencySymbol.EUR);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @Description("Should delete product by ID")
    public void testDeleteByID() {

        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "Product 1", "Product description 1", 2, new BigDecimal(100.00), CurrencySymbol.USD));
        products.add(new Product(2L, "Product 2", "Product description 2", 10, new BigDecimal(100.00), CurrencySymbol.ARS));

        List<Product> productsAfterDeletion = new ArrayList<>(products);
        productsAfterDeletion.remove(0); // Simulate the deletion of the first product

        Mockito.when(productRepository.findAll()).thenReturn(productsAfterDeletion);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(products.get(0)));

        productService.deleteProduct(1L);

        List<Product> result = (List<Product>) productService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(2L);
        assertThat(result.get(0).getName()).isEqualTo("Product 2");
        assertThat(result.get(0).getDescription()).isEqualTo("Product description 2");
        assertThat(result.get(0).getQuantity()).isEqualTo(10);
        assertThat(result.get(0).getPrice()).isEqualTo(new BigDecimal(100.00));
        assertThat(result.get(0).getCurrency()).isEqualTo(CurrencySymbol.ARS);

        verify(productRepository, times(1)).findAll();
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should calculate the total price in the target currency correctly")
    void shouldCalculateTotalPriceCorrectly() {
        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Product description 1")
                .quantity(2)
                .price(new BigDecimal("10.00"))
                .currency(CurrencySymbol.USD)
                .build();
        CurrencySymbol targetCurrency = CurrencySymbol.EUR;

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(CurrencySymbol.USD, CurrencySymbol.EUR);
        exchangeRateResponse.setConversion_rate(new BigDecimal("0.85"));

        when(exchangeFeignClient.getPairConversion(any(CurrencySymbol.class), any(CurrencySymbol.class)))
                .thenReturn("{ \"base_code\": \"USD\", \"target_code\": \"EUR\", \"conversion_rate\": 0.85 }");

        BigDecimal totalPrice = productService.calculateTotalPrice(product, targetCurrency);

        BigDecimal expectedTotalPrice = new BigDecimal("2").multiply(new BigDecimal("10.00")).multiply(new BigDecimal("0.85"));
        assertEquals(expectedTotalPrice, totalPrice);
    }

    @Test
    @DisplayName("Should fetch exchange rate successfully")
    public void shouldFetchExchangeRateSuccessfully() throws Exception {
        CurrencySymbol baseCurrency = CurrencySymbol.USD;
        CurrencySymbol targetCurrency = CurrencySymbol.EUR;
        BigDecimal conversionRate = new BigDecimal("0.98");

        ExchangeRateResponse mockResponse = new ExchangeRateResponse("USD", "EUR", conversionRate);
        String mockJsonResponse = new Gson().toJson(mockResponse);

        Mockito.when(exchangeFeignClient.getPairConversion(baseCurrency, targetCurrency)).thenReturn(mockJsonResponse);

        ExchangeRateResponse result = productService.fetchExchangeRate(baseCurrency, targetCurrency);

        assertEquals(mockResponse, result);
        verify(exchangeFeignClient, times(1)).getPairConversion(baseCurrency, targetCurrency);
    }

    @Test
    @DisplayName("Should throw InvalidCurrencyCodeException when FeignException is thrown")
    public void shouldThrowInvalidCurrencyCodeExceptionWhenFeignExceptionIsThrown() throws Exception {
        CurrencySymbol baseCurrency = CurrencySymbol.USD;
        CurrencySymbol targetCurrency = CurrencySymbol.EUR;

        Mockito.when(exchangeFeignClient.getPairConversion(baseCurrency, targetCurrency)).thenThrow(FeignException.class);

        assertThrows(InvalidCurrencyCodeException.class, () -> productService.fetchExchangeRate(baseCurrency, targetCurrency));
        verify(exchangeFeignClient, times(1)).getPairConversion(baseCurrency, targetCurrency);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when invalid currency type is provided")
    void shouldThrowIllegalArgumentException() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Product description 1")
                .quantity(2)
                .price(new BigDecimal("100.00"))
                .currency(CurrencySymbol.USD)
                .build();

        assertThrows(IllegalArgumentException.class, () -> productService.calculateTotalPrice(product, CurrencySymbol.valueOf("XYZ")));
    }

}