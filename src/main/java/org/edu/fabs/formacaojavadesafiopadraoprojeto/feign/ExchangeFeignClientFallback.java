package org.edu.fabs.formacaojavadesafiopadraoprojeto.feign;

import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.CurrencySymbol;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.ExchangeRateResponse;

import java.math.BigDecimal;

public class ExchangeFeignClientFallback implements ExchangeFeignClient {

    @Override
    public String getSupportedCurrencies(String code) {
        return code;
    }

    @Override
    public String getPairConversion(CurrencySymbol base_code, CurrencySymbol target_code) {
        return new ExchangeRateResponse(base_code, target_code).toString();
    }

    @Override
    public String getAmountConversion(String base_code, String target_code, BigDecimal amount) {
        return new ExchangeRateResponse(base_code, target_code, amount).toString();
    }

}
