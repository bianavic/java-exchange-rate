package org.edu.fabs.exchangerate.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;

import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        Integer quantity,
        @NotNull @DecimalMin("0")
        BigDecimal price,
        @NotNull
        CurrencySymbol currency) {

    public ProductDTO(Product model) {
        this(model.getName(), model.getDescription(), model.getQuantity(), model.getPrice(), model.getCurrency());
    }

    public Product toModel() {
        Product model = new Product();
        model.setName(this.name);
        model.setDescription(this.description);
        model.setQuantity(this.quantity);
        model.setPrice(this.price);
        model.setCurrency(this.currency);
        return model;
    }

}
