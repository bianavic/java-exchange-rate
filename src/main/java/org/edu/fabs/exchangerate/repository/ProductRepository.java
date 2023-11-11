package org.edu.fabs.exchangerate.repository;

import org.edu.fabs.exchangerate.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
