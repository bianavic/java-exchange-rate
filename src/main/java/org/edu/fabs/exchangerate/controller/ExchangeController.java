package org.edu.fabs.exchangerate.controller;

import com.google.gson.Gson;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.InvalidCurrencyCodeException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.model.ExchangeRateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Tag(name = "Exchange Rate Controller", description = "REST API for managing rates.")
@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeFeignClient exchangeFeignClient;

    @Operation(summary = "Get a list of exchange rates", description = "exchange rates from your base code to all the other currencies supported by exchange rate api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    @CrossOrigin
    @GetMapping("latest/{baseCode}")
    public String getLatest(@PathVariable("baseCode") String baseCode) {
        try {
            return exchangeFeignClient.getSupportedCurrencies(baseCode);
        } catch (Exception e) {
            throw new InvalidCurrencyCodeException(e.getMessage(), baseCode);
        }
    }

    @Operation(summary = "Get exchange rate between the codes and the conversion of the optional amount", description = "the exchange rate from your base code to the target currency you supplied, as well as a conversion of the amount you supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    @CrossOrigin
    @GetMapping("pair/{base_code}/{target_code}/{amount}")
    public ResponseEntity<String> exchangeAmount(
            @PathVariable("base_code") CurrencySymbol base_code,
            @PathVariable("target_code") CurrencySymbol target_code,
            @PathVariable("amount") BigDecimal amount) {
        try {
            String response = exchangeFeignClient.getAmountConversion(base_code.getName(),target_code.getName(), amount.setScale(2, RoundingMode.HALF_EVEN));
            Gson gson = new Gson();
            ExchangeRateResponse exchangeRateResponse = gson.fromJson(response, ExchangeRateResponse.class);
            return ResponseEntity.ok(gson.toJson(exchangeRateResponse));
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(null);
        }
    }

    @Operation(summary = "Get exchange rate from your base code to the other currency you supplied", description = "Get  exchange rate from your base code to the other currency you supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    @CrossOrigin
    @GetMapping("pair/{base_code}/{target_code}")
    public ResponseEntity<String> exchangePair(
            @PathVariable("base_code") CurrencySymbol base_code,
            @PathVariable("target_code") CurrencySymbol target_code) {
        try {
            String response = exchangeFeignClient.getPairConversion(base_code, target_code);
            Gson gson = new Gson();
            ExchangeRateResponse exchangeRateResponse = gson.fromJson(response, ExchangeRateResponse.class);
            return ResponseEntity.ok(gson.toJson(exchangeRateResponse));
        } catch (FeignException e) {
            return null;
        }
    }

}
