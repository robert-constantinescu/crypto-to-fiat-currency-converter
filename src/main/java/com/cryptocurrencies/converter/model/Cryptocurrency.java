package com.cryptocurrencies.converter.model;

import java.math.BigDecimal;

public class Cryptocurrency {

    private String thicker;
    private String name;
    private BigDecimal valueInUsd;

    public Cryptocurrency(String thicker, String name) {
        this.thicker = thicker;
        this.name = name;
    }

    public String getThicker() {
        return thicker;
    }

    public void setThicker(String thicker) {
        this.thicker = thicker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(BigDecimal valueInUsd) {
        this.valueInUsd = valueInUsd;
    }
}
