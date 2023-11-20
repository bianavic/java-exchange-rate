package org.edu.fabs.exchangerate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_products")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    @Column(precision = 13, scale = 2)
    private BigDecimal price;
    private CurrencySymbol currency;

    public Product(String name, String description, int quantity, BigDecimal price, CurrencySymbol currency) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.currency = currency;
    }

}
