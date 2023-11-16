package org.edu.fabs.exchangerate.handler;

public class InvalidCurrencyCodeException extends IllegalArgumentException {

    public InvalidCurrencyCodeException(String message, String invalidCode) {
        super("Invalid currency code. Currency code must be a valid ISO 4217 code: " + invalidCode);
    }

}
