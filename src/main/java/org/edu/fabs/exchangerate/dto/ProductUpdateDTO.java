package org.edu.fabs.exchangerate.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        @Min(value = 1)
        Integer quantity,
        @DecimalMin("0")
        @Positive
        BigDecimal price,
        CurrencySymbol currency) {

    public ProductUpdateDTO(Product model) {
        this(model.getQuantity(), model.getPrice(), model.getCurrency());
    }

    public Product toModel() {
        Product model = new Product();
        model.setQuantity(this.quantity);
        model.setPrice(this.price);
        model.setCurrency(this.currency);
        return model;
    }

}
