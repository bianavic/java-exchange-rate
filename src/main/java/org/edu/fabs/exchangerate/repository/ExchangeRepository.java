package org.edu.fabs.exchangerate.repository;

import org.edu.fabs.exchangerate.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<ExchangeRate, Long> {}
