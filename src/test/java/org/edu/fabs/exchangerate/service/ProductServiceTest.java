package org.edu.fabs.exchangerate.service;

import org.edu.fabs.exchangerate.dto.ProductUpdateDTO;
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
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Product Service Tests")
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**

     *     Product updateProduct(Long id, Product productToUpdate);
     *     void deleteProduct(Long id);
     *     BigDecimal calculateTotalPrice(Product product, CurrencySymbol targetCurrency);
     */

//    @BeforeEach
//    void setUp() {
//        var product1 = Product.builder()
//                .id(1L)
//                .name("Product 1")
//                .description("Product description 1")
//                .quantity(1)
//                .price(new BigDecimal(100.00))
//                .currency(CurrencySymbol.USD);
//        var product2 = Product.builder()
//                .id(2L)
//                .name("Product 2")
//                .description("Product description 2")
//                .quantity(10)
//                .price(new BigDecimal(700.00))
//                .currency(CurrencySymbol.BRL);
//    }

    @Test
    @Description("Test getAll() method")
    public void testGetAll() {

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
    @Description("Test getById method when exists ID at database")
    public void testGetByID() {

        Product product = new Product(1L, "Product 1", "Product description 1", 2, new BigDecimal(100.00), CurrencySymbol.USD);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getById(1L);

        if (result.isPresent()) {
            assertThat(result.get()).isEqualTo(product);
        } else {
            fail("Expected Product object but found empty Optional");
        }

        // verify if repository method is called at least 1 time
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @Description("Test getById method when ID does not exists")
    public void testGetByIDNoExistentID() {

        Long productId = 1L;
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Resource ID not found " + productId)
                .extracting("status")
                .isEqualTo(HttpStatus.NOT_FOUND);

        // verify if repository method is called at least 1 time
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @Description("Test successfully add new Product")
    public void testAddProduct() {

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
    @DisplayName("Test successfully updating product information by ID")
    public void testUpdateProduct() {
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

        // Verify repository method calls
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @Description("Test delete product by ID")
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

}