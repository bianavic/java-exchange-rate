package org.edu.fabs.exchangerate.client;

import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.edu.fabs.exchangerate.utils.Constants.BASE_CODE;
import static org.edu.fabs.exchangerate.utils.Constants.MOCKED_PAIR_AMOUNT_RESPONSE;
import static org.edu.fabs.exchangerate.utils.Constants.MOCKED_PAIR_CONVERSION_RESPONSE;
import static org.edu.fabs.exchangerate.utils.Constants.MOCKED_SUPPORTED_CURRENCIES_RESPONSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Integration tests for exchange rate api")
class ExchangeFeignClientTest {

    @Autowired
    private ExchangeFeignClient exchangeFeignClient;

    @Test
    @DisplayName("Should Get Supported Currencies when make request to ExchangeRate API")
    void shouldGetSupportedCurrenciesShouldReturnDataFromClient() {
        exchangeFeignClient = Mockito.spy(exchangeFeignClient);

        String expectedResponse = MOCKED_SUPPORTED_CURRENCIES_RESPONSE;
        String actual = exchangeFeignClient.getSupportedCurrencies(BASE_CODE);

        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied")
    void testGetPairConversion() {
        exchangeFeignClient = Mockito.spy(exchangeFeignClient);

        String expectedResponse = MOCKED_PAIR_CONVERSION_RESPONSE;
        String actual = exchangeFeignClient.getPairConversion(CurrencySymbol.BRL, CurrencySymbol.EUR);

        assertEquals(expectedResponse, actual);
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied, as well as a conversion of the amount supplied")
    void testGetAmountConversion() {
        exchangeFeignClient = Mockito.spy(exchangeFeignClient);

        String expectedResponse = MOCKED_PAIR_AMOUNT_RESPONSE;
        String actual = exchangeFeignClient.getAmountConversion("BRL", "EUR", BigDecimal.valueOf(1000.00));

        assertEquals(expectedResponse, actual);
    }

}
