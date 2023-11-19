package org.edu.fabs.exchangerate.handler;

public class InvalidCurrencyCodeException extends RuntimeException {

    private final String invalidCode;

    public InvalidCurrencyCodeException(String message, String invalidCode) {
        super(message);
        this.invalidCode = invalidCode;
    }

    public String getInvalidCode() {
        return invalidCode;
    }

}
