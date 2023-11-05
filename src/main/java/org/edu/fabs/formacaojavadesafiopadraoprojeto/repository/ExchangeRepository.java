package org.edu.fabs.formacaojavadesafiopadraoprojeto.repository;

import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<ExchangeRate, Long> {}
