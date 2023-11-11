package org.edu.fabs.exchangerate.service;

import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.Product;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Iterable<Product> getAll();
    Optional<Product> getById(Long id);
    Product addProduct(Product product);
    Product updateProduct(Long id, Product productToUpdate);
    void deleteProduct(Long id);
    BigDecimal calculateTotalPrice(Product product, CurrencySymbol targetCurrency);

}
