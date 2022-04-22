package com.cryptocurrencies.converter.controller.dto;

public class ConverterInfoDTO {

    private String coinMarketCapId;
    private String ip;
    private String currency;
    private String valueInCurrency;
    private String valueInUsd;

    public String getCoinMarketCapId() {
        return coinMarketCapId;
    }

    public void setCoinMarketCapId(String coinMarketCapId) {
        this.coinMarketCapId = coinMarketCapId;
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

    public String getValueInCurrency() {
        return valueInCurrency;
    }

    public void setValueInCurrency(String valueInCurrency) {
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
                "valueInUsd='" + coinMarketCapId + '\'' +
                ", ip='" + ip + '\'' +
                ", objectClass='" + this.getClass().getSimpleName() + '\'' +
                "}";
    }
}
