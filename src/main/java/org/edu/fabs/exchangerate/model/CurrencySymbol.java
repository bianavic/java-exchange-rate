package org.edu.fabs.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.edu.fabs.exchangerate.handler.InvalidCurrencyCodeException;

@AllArgsConstructor
public enum CurrencySymbol {

    ARS("ARS", "Argentina Peso"),
    BRL("BRL", "Brazil Real"),
    EUR("EUR", "Euro Member Countries"),
    GBP("GBP", "United Kingdom Pound"),
    JPY("JYP", "Japan Yen"),
    USD("USD", "United States Dollar");

    @Pattern(regexp = "^[A-Za-z]{3}$", message = "currency must be a valid ISO 4217 currency code")
    private final String name;

    @Getter
    private final String displayName;

    @JsonCreator
    public static CurrencySymbol fromString(String name) throws InvalidCurrencyCodeException {
        for (CurrencySymbol symbol : CurrencySymbol.values()) {
            if (symbol.name.equals(name)) {
                return symbol;
            }
        }
        throw new InvalidCurrencyCodeException("Invalid currency code: ", name);
    }

    @JsonValue
    public String getName() {
        return name;
    }

}
