package org.edu.fabs.exchangerate.handler;

public class InvalidCurrencyCodeException extends IllegalArgumentException {

    public InvalidCurrencyCodeException(String invalidCode) {
        super("Invalid currency code. " + invalidCode);
    }

}
