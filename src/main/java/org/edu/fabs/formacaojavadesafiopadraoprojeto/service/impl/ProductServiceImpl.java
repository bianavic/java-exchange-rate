package org.edu.fabs.formacaojavadesafiopadraoprojeto.service.impl;

import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.Product;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.repository.ProductRepository;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
//        saveProductPriceConverted(product);
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

//    private void convertProductPrice(Product product) {
//        BigDecimal price = product.getPrice();
//        ExchangeRate exchangeRate = exchangeRepository.findById(product.getId()).orElseThrow(() -> {
//            ExchangeRate productConverted = exchangeFeignClient.getAmountConversion(baseCode, targetCode, product.getPrice());
//            exchangeRepository.save(productConverted);
//        });
//        product.setPrice(exchangeRate);
//        productRepository.save(product);
//    }

}
