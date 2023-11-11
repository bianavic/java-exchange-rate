package org.edu.fabs.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CurrencySymbol {

    ARS("ARS", "Argentina Peso"),
    BRL("BRL", "Brazil Real"),
    EUR("EUR", "Euro Member Countries"),
    GBP("GBP", "United Kingdom Pound"),
    JPY("JYP", "Japan Yen"),
    USD("USD", "United States Dollar");

    private String name;

    private String displayName;

}
