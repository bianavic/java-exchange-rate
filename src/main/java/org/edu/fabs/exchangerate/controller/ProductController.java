package org.edu.fabs.exchangerate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.edu.fabs.exchangerate.dto.ProductDTO;
import org.edu.fabs.exchangerate.dto.ProductUpdateDTO;
import org.edu.fabs.exchangerate.handler.NotFoundException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;
import org.edu.fabs.exchangerate.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@Tag(name = "Product Controller", description = "REST API for managing products.")
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @CrossOrigin
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all registered products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<Iterable<Product>> getAllProducts() {
            return ResponseEntity.ok(productService.getAll());
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID", description = "Retrieve a specific product based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
//            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @CrossOrigin
    @PostMapping
    @Operation(summary = "Add a new product", description = "Add a new product and return the created product's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added successfully"),
//            @ApiResponse(responseCode = "422", description = "Invalid product data provided")
    })
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        Product newProduct = productService.addProduct(productDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newProduct.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ProductDTO(newProduct));
    }

    @CrossOrigin
    @Operation(summary = "Update product information", description = "Update a product quantity, price and currency by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateDTO> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDTO productToUpdate) {
        Product productUpdated = productService.updateProduct(id, productToUpdate.toModel());
        return ResponseEntity.ok(new ProductUpdateDTO(productUpdated));
    }

    @Operation(summary = "Delete a product information", description = "Remove a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get product total price converted to a chosen currency", description = "Get total price of your registered product converted to another coin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    @CrossOrigin
    @GetMapping("/{id}/totalPrice/{targetCurrency}")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long id, @PathVariable CurrencySymbol targetCurrency) {
        Product product = productService.getById(id).orElseThrow();
        BigDecimal totalPrice = productService.calculateTotalPrice(product, targetCurrency);
        return ResponseEntity.ok(totalPrice);
    }


}
