package org.edu.fabs.formacaojavadesafiopadraoprojeto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"base_code", "target_code", "conversion_rate", "conversion_result", "time_last_update_utc"})
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
    private LocalDateTime time_last_update_utc;

    private LocalDateTime localDateTime = LocalDateTime.now();

    public ExchangeRateResponse(CurrencySymbol base_code, CurrencySymbol target_code) {
        this.base_code = base_code;
        this.target_code = target_code;
    }

    public ExchangeRateResponse(String base_code, String target_code, BigDecimal amount) {
        this.base_code = getBase_code();
        this.target_code = getTarget_code();
    }

    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
                "localDateTime=" + localDateTime +
                ", base_code=" + base_code +
                ", target_code=" + target_code +
                ", conversion_rate=" + conversion_rate +
                ", conversion_result=" + conversion_result +
                ", time_last_update_utc=" + time_last_update_utc +
                '}';
    }

}
