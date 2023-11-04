package org.edu.fabs.formacaojavadesafiopadraoprojeto.controller;

import feign.FeignException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.feign.ExchangeFeignClient;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.CurrencySymbol;
import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.ExchangeRateResponse;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("latest/{baseCode}")
    public String getLatest(@PathVariable("baseCode") String baseCode) {
        return exchangeFeignClient.getSupportedCurrencies(baseCode);
    }

    @GetMapping("pair/{base_code}/{target_code}/{amount}")
    public ResponseEntity<String> exchangeAmount(
            @PathVariable("base_code") CurrencySymbol base_code,
            @PathVariable("target_code") CurrencySymbol target_code,
            @PathVariable("amount") BigDecimal amount) {
        try {
            String response = exchangeFeignClient.getAmountConversion(base_code.getName(),target_code.getName(), amount.setScale(2, RoundingMode.HALF_EVEN)).toString();
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(null);
        }
    }

    @GetMapping("pair/{base_code}/{target_code}")
    public ResponseEntity<String> exchangePair(
            @PathVariable("base_code") CurrencySymbol base_code,
            @PathVariable("target_code") CurrencySymbol target_code) {
        try {
            String response = exchangeFeignClient.getPairConversion(base_code, target_code);
            return ResponseEntity.ok(response);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(null);
        }
    }

}
