package org.edu.fabs.formacaojavadesafiopadraoprojeto.feign;

import org.edu.fabs.formacaojavadesafiopadraoprojeto.model.CurrencySymbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "exchangeapi", url = "${exchange.url}", fallback = ExchangeFeignClientFallback.class)
public interface ExchangeFeignClient {

    @GetMapping("/latest/{code}")
    String getSupportedCurrencies(@PathVariable("code") String code);

    @GetMapping("/pair/{base_code}/{target_code}")
    String getPairConversion(@PathVariable("base_code") CurrencySymbol base_code, @PathVariable("target_code") CurrencySymbol target_code);

    @GetMapping("/pair/{baseCode}/{targetCode}/{amount}")
    String getAmountConversion(@PathVariable("baseCode") String baseCode, @PathVariable("targetCode") String targetCode, @PathVariable("amount") BigDecimal amount);

}
