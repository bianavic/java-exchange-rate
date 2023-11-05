package org.edu.fabs.formacaojavadesafiopadraoprojeto.service;

import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.CurrencySymbol;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Iterable<Product> getAll();
    Optional<Product> getById(Long id);
    void addProduct(Product product);
    void updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    BigDecimal calculateTotalPrice(Product product, CurrencySymbol targetCurrency);

}
