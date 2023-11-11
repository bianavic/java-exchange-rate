package org.edu.fabs.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table
@Entity
@ToString
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("base_code")
    @Enumerated(EnumType.STRING)
    private CurrencySymbol base_code;
    @JsonProperty("target_code")
    @Enumerated(EnumType.STRING)
    private CurrencySymbol target_code;
    @JsonProperty("conversion_rate")
    private BigDecimal conversion_rate;
    @JsonProperty("conversion_result")
    private BigDecimal conversion_result;
    @JsonProperty("time_last_update_utc")
    private LocalDateTime time_last_update_utc;
    private LocalDateTime localDateTime = LocalDateTime.now();

}
