package org.edu.fabs.exchangerate.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.edu.fabs.exchangerate.dto.ExchangeRateResponse;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.InvalidAmountException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.service.ExchangeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeFeignClient exchangeFeignClient;

    private Gson gson;

    @Override
    public String getAmountConversion(CurrencySymbol base_code, CurrencySymbol target_code, BigDecimal amount) {
        if (isValidAmount(amount)) {
            return exchangeFeignClient.getAmountConversion(base_code.getName(), target_code.getName(), amount.setScale(2, RoundingMode.HALF_EVEN));
        } else throw new InvalidAmountException("Invalid amount: ", amount);
    }

    @Override
    public String getPairConversion(CurrencySymbol base_code, CurrencySymbol target_code) {
        return exchangeFeignClient.getPairConversion(base_code, target_code);
    }

    @Override
    public ExchangeRateResponse convertToExchangeRateResponse(String response) {
        return gson.fromJson(response, ExchangeRateResponse.class);
    }

    @Override
    public String convertToJson(ExchangeRateResponse exchangeRateResponse) {
        return gson.toJson(exchangeRateResponse);
    }

    public boolean isValidAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }

}
