package org.edu.fabs.exchangerate.service;

import org.edu.fabs.exchangerate.dto.ExchangeRateResponse;
import org.edu.fabs.exchangerate.model.CurrencySymbol;

import java.math.BigDecimal;

public interface ExchangeService {

    String getAmountConversion(CurrencySymbol base_code, CurrencySymbol target_code, BigDecimal amount);

    String getPairConversion(CurrencySymbol base_code, CurrencySymbol target_code);

    ExchangeRateResponse convertToExchangeRateResponse(String response);

    String convertToJson(ExchangeRateResponse exchangeRateResponse);

}
