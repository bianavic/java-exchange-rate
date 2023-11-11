package org.edu.fabs.exchangerate.dto;

import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;

import java.math.BigDecimal;

public record ProductDTO(
        String name,
        String description,
        Integer quantity,
        BigDecimal price,
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
