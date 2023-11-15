package org.edu.fabs.exchangerate.service.impl;

import com.google.gson.Gson;
import feign.FeignException;
import org.apache.commons.lang3.EnumUtils;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.ConversionException;
import org.edu.fabs.exchangerate.handler.InvalidCurrencyCodeException;
import org.edu.fabs.exchangerate.handler.ResourceNotFoundException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.ExchangeRateResponse;
import org.edu.fabs.exchangerate.model.Product;
import org.edu.fabs.exchangerate.repository.ProductRepository;
import org.edu.fabs.exchangerate.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
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

    @Transactional(readOnly = true)
    @Override
    public Iterable<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> getById(Long id) {
        return Optional.ofNullable(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Transactional
    @Override
    public Product addProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    @Transactional
    @Override
    public Product updateProduct(Long id, Product productToUpdate) {

        Optional<Product> optionalProduct = Optional.ofNullable(this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id)));

        if (isValidCurrencyType(productToUpdate.getCurrency().getName())) {
            throw new InvalidCurrencyCodeException("Invalid currency type passed");
        }

        if (optionalProduct.isPresent()) {
            Product productDB = optionalProduct.get();
            productDB.setQuantity(productToUpdate.getQuantity());
            productDB.setPrice(productToUpdate.getPrice());
            productDB.setCurrency(productToUpdate.getCurrency());
            return this.productRepository.save(productDB);
        } else {
            throw new NoSuchElementException("Product not found " + productToUpdate);
        }
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public BigDecimal calculateTotalPrice(Product product, CurrencySymbol targetCurrency) {
        BigDecimal conversionRate = BigDecimal.ZERO;
        if (isValidCurrencyType(targetCurrency.getName())) {
            throw new InvalidCurrencyCodeException("Invalid currency type passed");
        }
        try {
            String response = exchangeFeignClient.getPairConversion(product.getCurrency(), targetCurrency);
            Gson gson = new Gson();
            ExchangeRateResponse exchangeRateResponse = gson.fromJson(response, ExchangeRateResponse.class);
            conversionRate = exchangeRateResponse.getConversion_rate();
        } catch (FeignException e) {
            throw new ConversionException("Failed to get conversion rate");
        }
        return product.getPrice().multiply(new BigDecimal(product.getQuantity())).multiply(conversionRate);
    }

    private boolean isValidCurrencyType(String currencyType) {
        return !EnumUtils.isValidEnum(CurrencySymbol.class, currencyType);
    }

}
