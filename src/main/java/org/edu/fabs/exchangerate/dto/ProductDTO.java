package org.edu.fabs.exchangerate.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;

import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank(message = "name is mandatory")
        @NotNull(message = "name is mandatory")
        @Size(min = 3, max = 50, message = "name must have at least 3 and max 50 characters")
        String name,
        String description,
        @NotNull(message = "quantity is mandatory")
        @Positive
        Integer quantity,
        @NotNull(message = "price is mandatory")
        @Positive
        @DecimalMin("0")
        BigDecimal price,
        @NotNull(message = "currency is mandatory")
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
