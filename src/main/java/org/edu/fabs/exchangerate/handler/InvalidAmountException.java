package org.edu.fabs.exchangerate.handler;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InvalidAmountException extends RuntimeException {

    private final BigDecimal invalidAmount;

    public InvalidAmountException(String message, BigDecimal invalidAmount) {
        super(message);
        this.invalidAmount = invalidAmount;
    }

}
