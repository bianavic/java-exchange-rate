package org.edu.fabs.formacaojavadesafiopadraoprojeto.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.handler.ResourceNotFoundException;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.CurrencySymbol;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.Product;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@Tag(name = "Product Controller", description = "REST API for managing products.")
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
            return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        productService.updateProduct(id, product);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/totalPrice/{targetCurrency}")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long id, @PathVariable CurrencySymbol targetCurrency) {
        Product product = productService.getById(id).orElseThrow(() -> new ResourceNotFoundException("product"));
        BigDecimal totalPrice = productService.calculateTotalPrice(product, targetCurrency);
        return ResponseEntity.ok(totalPrice);
    }


}
