package org.edu.fabs.exchangerate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.edu.fabs.exchangerate.model.CurrencySymbol;

import java.math.BigDecimal;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateResponse {

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
    private String time_last_update_utc;

    public ExchangeRateResponse(CurrencySymbol base_code, CurrencySymbol target_code) {
        this.base_code = CurrencySymbol.valueOf(base_code.getName().toUpperCase());
        this.target_code = CurrencySymbol.valueOf(target_code.getName().toUpperCase());
    }

    public ExchangeRateResponse(String base_code, String target_code, BigDecimal amount) {
        this.base_code = CurrencySymbol.valueOf(base_code);
        this.target_code = CurrencySymbol.valueOf(target_code.toUpperCase());
        this.conversion_result = amount;
    }

    public ExchangeRateResponse(CurrencySymbol base_code, CurrencySymbol target_code, BigDecimal amount) {
        this.base_code = base_code;
        this.target_code = target_code;
        this.conversion_result = amount;
    }

}
