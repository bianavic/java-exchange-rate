package org.edu.fabs.exchangerate.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.google.gson.Gson;
import org.edu.fabs.exchangerate.dto.ExchangeRateResponse;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.handler.InvalidAmountException;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.edu.fabs.exchangerate.service.impl.ExchangeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest
@ActiveProfiles("test")
@DisplayName("Exchange Service Test")
class ExchangeServiceTest {

    @Mock
    private ExchangeFeignClient exchangeFeignClient;

    @Mock
    private Gson gson;
    @InjectMocks
    private ExchangeServiceImpl exchangeService;
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(options().port(8282));
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockServer.port());
        System.setProperty("feign.client.config.default.url", "http://localhost:8282");
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied, as well as a conversion of the amount supplied")
    void testGetAmountConversion() {

        CurrencySymbol base_code = CurrencySymbol.USD;
        CurrencySymbol target_code = CurrencySymbol.EUR;
        BigDecimal amount = new BigDecimal("100.00");
        String expectedResponse = "response";

        when(exchangeFeignClient.getAmountConversion(base_code.getName(), target_code.getName(), amount.setScale(2, RoundingMode.HALF_EVEN))).thenReturn(expectedResponse);

        String actualResponse = exchangeService.getAmountConversion(base_code, target_code, amount);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Should return InvalidAmountException when an invalid amount is supplied")
    void testGetAmountConversionException() {

        CurrencySymbol base_code = CurrencySymbol.USD;
        CurrencySymbol target_code = CurrencySymbol.EUR;
        BigDecimal invalidAmount = new BigDecimal(-100);

        assertThrows(InvalidAmountException.class, () -> exchangeService.getAmountConversion(base_code, target_code, invalidAmount));
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied")
    void testGetPairConversion() {

        String expectedResponse = "response";

        when(exchangeFeignClient.getPairConversion(CurrencySymbol.BRL, CurrencySymbol.EUR)).thenReturn(expectedResponse);
        String actualResponse = exchangeService.getPairConversion(CurrencySymbol.BRL, CurrencySymbol.EUR);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Should convert a JSON string to an ExchangeRateResponse object")
    void testConvertToExchangeRateResponse() {

        String response = "{\"base_code\":\"USD\",\"target_code\":\"EUR\",\"conversion_rate\":1.2,\"conversion_result\":120.0,\"time_last_update_utc\":\"2023-11-18T00:00:00Z\"}";
        ExchangeRateResponse expectedResponse = new ExchangeRateResponse(CurrencySymbol.USD, CurrencySymbol.EUR, BigDecimal.valueOf(120.0));

        when(gson.fromJson(response, ExchangeRateResponse.class)).thenReturn(expectedResponse);

        ExchangeRateResponse actualResponse = exchangeService.convertToExchangeRateResponse(response);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("Should convert ExchangeRateResponse object to a JSON string")
    void testConvertToJson() {

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(CurrencySymbol.USD, CurrencySymbol.EUR, BigDecimal.valueOf(120.0));
        String expectedJson = "{\"base_code\":\"USD\",\"target_code\":\"EUR\",\"conversion_rate\":1.2,\"conversion_result\":120.0,\"time_last_update_utc\":\"2023-11-18T00:00:00Z\"}";

        when(gson.toJson(exchangeRateResponse)).thenReturn(expectedJson);

        String actualJson = exchangeService.convertToJson(exchangeRateResponse);

        assertEquals(expectedJson, actualJson);
    }

}