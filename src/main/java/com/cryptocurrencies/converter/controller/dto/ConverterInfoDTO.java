package com.cryptocurrencies.converter.controller.dto;

public class ConverterInfoDTO {

    private String cryptoSymbol;
    private String ip;
    private String currency;
    private Double valueInCurrency;
    private String valueInUsd;

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }

    public void setCryptoSymbol(String cryptoSymbol) {
        this.cryptoSymbol = cryptoSymbol;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getValueInCurrency() {
        return valueInCurrency;
    }

    public void setValueInCurrency(Double valueInCurrency) {
        this.valueInCurrency = valueInCurrency;
    }

    public String getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(String valueInUsd) {
        this.valueInUsd = valueInUsd;
    }

    @Override
    public String toString() {
        return "{" +
                "valueInUsd='" + cryptoSymbol + '\'' +
                ", ip='" + ip + '\'' +
                ", objectClass='" + this.getClass().getSimpleName() + '\'' +
                "}";
    }
}
