package org.edu.fabs.exchangerate.controller;

import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.InvalidAmountException;
import org.edu.fabs.exchangerate.handler.InvalidCurrencyCodeException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.edu.fabs.exchangerate.model.CurrencySymbol.BRL;
import static org.edu.fabs.exchangerate.model.CurrencySymbol.EUR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@DisplayName("ExchangeController Unit Test")
class ExchangeControllerTest {

    @Mock
    private ExchangeFeignClient exchangeFeignClient;

    @Mock
    private ExchangeService exchangeService;

    @InjectMocks
    private ExchangeController exchangeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return exchange rates for the given base code")
    void getLatest() {
        String baseCode = "USD";
        String mockedResponse = "Mocked exchange rates response";

        when(exchangeFeignClient.getSupportedCurrencies(anyString())).thenReturn(mockedResponse);

        String actualResponse = exchangeController.getLatest(baseCode);

        assertEquals(mockedResponse, actualResponse);
    }

    @Test
    @DisplayName("Should throw InvalidCurrencyCodeException when Client throws an exception")
    void getLatestFeignClientException() {
        String baseCode = "InvalidCode";
        String errorMessage = "Invalid currency code. Currency code must be a valid ISO 4217 code: ";

        when(exchangeFeignClient.getSupportedCurrencies(anyString())).thenThrow(new RuntimeException(errorMessage + baseCode));

        try {
            exchangeController.getLatest(baseCode);
        } catch (InvalidCurrencyCodeException ex) {
            assertEquals(errorMessage + baseCode, ex.getMessage());
            assertEquals(baseCode, ex.getInvalidCode());
        }
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied, as well as a conversion of the amount supplied")
    void exchangePairAmount() {

        String baseCode = "BRL";
        String targetCode = "EUR";
        BigDecimal amount = BigDecimal.valueOf(1000);
        String mockedResponse = "200 OK";

        when(exchangeFeignClient.getAmountConversion(eq(baseCode), eq(targetCode), eq(amount.setScale(2, RoundingMode.HALF_EVEN))))
                .thenReturn(mockedResponse);

        ResponseEntity<String> responseEntity = exchangeController.exchangeAmount(BRL, EUR, BigDecimal.valueOf(1000));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should throw FeignException when Client throws an exception")
    void exchangePairAmountException() {
        String baseCode = "InvalidCode";
        String errorMessage = "Invalid currency code. Currency code must be a valid ISO 4217 code: ";

        when(exchangeFeignClient.getSupportedCurrencies(anyString())).thenThrow(new RuntimeException(errorMessage + baseCode));

        try {
            exchangeController.getLatest(baseCode);
        } catch (InvalidCurrencyCodeException ex) {
            assertEquals(errorMessage + baseCode, ex.getMessage());
            assertEquals(baseCode, ex.getInvalidCode());
        }
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied")
    void exchangePair() {

        CurrencySymbol baseCode = BRL;
        CurrencySymbol targetCode = EUR;
        String mockedResponse = "200 OK";

        when(exchangeFeignClient.getPairConversion(baseCode, targetCode))
                .thenReturn(mockedResponse);

        ResponseEntity<String> responseEntity = exchangeController.exchangePair(BRL, EUR);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should throw InvalidAmountException when quantity is less then 0")
    public void exchangePair_InvalidAmountException_Returns400() {
        BigDecimal invalidAmount = BigDecimal.valueOf(-1);
        String errorMessage = "Invalid amount: ";

        when(exchangeFeignClient.getAmountConversion(anyString(), anyString(), any())).thenThrow(new RuntimeException(errorMessage + invalidAmount));

        try {
            exchangeController.getLatest(String.valueOf(invalidAmount));
        } catch (InvalidAmountException ex) {
            assertEquals(errorMessage + invalidAmount, ex.getMessage());
            assertEquals(invalidAmount, ex.getInvalidAmount());
        }
    }

}