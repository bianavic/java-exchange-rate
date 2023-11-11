package org.edu.fabs.exchangerate.service.impl;

import com.google.gson.Gson;
import feign.FeignException;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.BusinessException;
import org.edu.fabs.exchangerate.handler.ResourceNotFoundException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.ExchangeRateResponse;
import org.edu.fabs.exchangerate.model.Product;
import org.edu.fabs.exchangerate.repository.ProductRepository;
import org.edu.fabs.exchangerate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ExchangeFeignClient exchangeFeignClient;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ExchangeFeignClient exchangeFeignClient) {
        this.productRepository = productRepository;
        this.exchangeFeignClient = exchangeFeignClient;
    }

    @Override
    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product addProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    @Override
    public Product updateProduct(Long id, Product productToUpdate) {

        Optional<Product> optionalProduct = this.productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product productDB = optionalProduct.get();
            productDB.setQuantity(productToUpdate.getQuantity());
            productDB.setPrice(productToUpdate.getPrice());
            productDB.setCurrency(productToUpdate.getCurrency());
            return this.productRepository.save(productDB);
        } else {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public BigDecimal calculateTotalPrice(Product product, CurrencySymbol targetCurrency) {
        BigDecimal conversionRate = BigDecimal.ZERO;
        try {
            String response = exchangeFeignClient.getPairConversion(product.getCurrency(), targetCurrency);
            Gson gson = new Gson();
            ExchangeRateResponse exchangeRateResponse = gson.fromJson(response, ExchangeRateResponse.class);
            conversionRate = exchangeRateResponse.getConversion_rate();
        } catch (FeignException e) {
            throw new BusinessException("failed to get exchange rate", e);
        }
        return product.getPrice().multiply(new BigDecimal(product.getQuantity())).multiply(conversionRate);
    }

}
