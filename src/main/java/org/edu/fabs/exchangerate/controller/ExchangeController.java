package org.edu.fabs.exchangerate.controller;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.edu.fabs.exchangerate.dto.ExchangeRateResponse;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.InvalidCurrencyCodeException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "Exchange Rate Controller", description = "REST API for managing rates.")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeFeignClient exchangeFeignClient;
    private final ExchangeService exchangeService;

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
            String response = exchangeService.getAmountConversion(base_code,target_code, amount);
            ExchangeRateResponse exchangeRateResponse = exchangeService.convertToExchangeRateResponse(response);
            String jsonResponse = exchangeService.convertToJson(exchangeRateResponse);
            return ResponseEntity.ok(jsonResponse);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.contentUTF8());
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
            String response = exchangeService.getPairConversion(base_code, target_code);
            ExchangeRateResponse exchangeRateResponse = exchangeService.convertToExchangeRateResponse(response);
            return ResponseEntity.ok(exchangeService.convertToJson(exchangeRateResponse));
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(null);
        }
    }

}
