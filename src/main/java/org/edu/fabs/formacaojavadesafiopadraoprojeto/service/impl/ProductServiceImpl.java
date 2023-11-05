package org.edu.fabs.formacaojavadesafiopadraoprojeto.service.impl;

import com.google.gson.Gson;
import feign.FeignException;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.feign.ExchangeFeignClient;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.handler.BusinessException;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.CurrencySymbol;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.ExchangeRateResponse;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.Product;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.repository.ProductRepository;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.service.ProductService;
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
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            product.setName(product.getName());
            product.setDescription(product.getDescription());
            product.setQuantity(product.getQuantity());
            product.setPrice(product.getPrice());
            productRepository.save(product);
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
