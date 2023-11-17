package org.edu.fabs.exchangerate.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.edu.fabs.exchangerate.feign.ExchangeFeignClient;
import org.edu.fabs.exchangerate.model.CurrencySymbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.edu.fabs.exchangerate.utils.Constants.BASE_CODE;
import static org.edu.fabs.exchangerate.utils.Constants.MOCKED_PAIR_AMOUNT_RESPONSE;
import static org.edu.fabs.exchangerate.utils.Constants.MOCKED_PAIR_CONVERSION_RESPONSE;
import static org.edu.fabs.exchangerate.utils.Constants.MOCKED_SUPPORTED_CURRENCIES_RESPONSE;
import static org.edu.fabs.exchangerate.utils.Constants.TARGET_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExchangeControllerTest {

    @Autowired
    private ExchangeFeignClient exchangeFeignClient;

    @Test
    @DisplayName("Should return all supported currencies when make request to ExchangeRate API")
    void getLatest() {

        String mockedResponse = MOCKED_SUPPORTED_CURRENCIES_RESPONSE;

        WireMockServer wireMockServer = new WireMockServer(options().port(8282));
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockServer.port());
        WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/latest/BRL"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(mockedResponse)
                        )
        );
        String actualResponse = exchangeFeignClient.getSupportedCurrencies("BRL");
        assertEquals(mockedResponse, actualResponse);

        wireMockServer.stop();
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied")
    void exchangeAmount() {

        String mockedResponse = MOCKED_PAIR_CONVERSION_RESPONSE;
        WireMockServer wireMockServer = new WireMockServer(options().port(8282));
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockServer.port());
        WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/pair/BRL/EUR"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(mockedResponse)
                        )
        );
        String actualResponse = exchangeFeignClient.getPairConversion(CurrencySymbol.BRL, CurrencySymbol.EUR);
        assertEquals(mockedResponse, actualResponse);

        wireMockServer.stop();
    }

    @Test
    @DisplayName("Should return the exchange rate from a base code to the other currency supplied, as well as a conversion of the amount supplied")
    void exchangePair() {

        String mockedResponse = MOCKED_PAIR_AMOUNT_RESPONSE;
        WireMockServer wireMockServer = new WireMockServer(options().port(8282));
        wireMockServer.start();

        WireMock.configureFor("localhost", wireMockServer.port());
        WireMock.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/pair/BRL/EUR/1000"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(mockedResponse)
                        )
        );
        String actualResponse = exchangeFeignClient.getAmountConversion(BASE_CODE, TARGET_CODE, BigDecimal.valueOf(1000));
        assertEquals(mockedResponse, actualResponse);

        wireMockServer.stop();
    }

}