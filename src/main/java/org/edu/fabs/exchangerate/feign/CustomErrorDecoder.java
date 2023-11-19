package org.edu.fabs.exchangerate.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.edu.fabs.exchangerate.handler.InvalidCurrencyCodeException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            String currencyCode = response.request().url().split("/")[6]; // Get the currency code from the URL
            return new InvalidCurrencyCodeException("Invalid currency code. Currency code must be a valid ISO 4217 code: ", currencyCode);
        }
        return new Exception("Generic exception");
    }

}
