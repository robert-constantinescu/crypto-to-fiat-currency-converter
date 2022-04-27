package com.cryptocurrencies.converter.controller.dto;

import java.text.NumberFormat;
import java.util.Locale;

public class ConverterInfoDTO {

    private String cryptoSymbol;
    private String ip;
    private String fiatCurrency;
    private Double valueInFiatCurrency;
    private Locale ipLocale;
    private String errorMessage;

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

    public String getFiatCurrency() {
        return fiatCurrency;
    }

    public void setFiatCurrency(String fiatCurrency) {
        this.fiatCurrency = fiatCurrency;
    }

    public Double getValueInFiatCurrency() {
        return valueInFiatCurrency;
    }

    public void setValueInFiatCurrency(Double valueInFiatCurrency) {
        this.valueInFiatCurrency = valueInFiatCurrency;
    }

    public Locale getIpLocale() {
        return ipLocale;
    }

    public void setIpLocale(Locale ipLocale) {
        this.ipLocale = ipLocale;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String displayFiatCurrencyValueInLocalFormat() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.ipLocale);
        if (this.valueInFiatCurrency < 1) {
            formatter.setMinimumFractionDigits(8);
        }
        String formattedValue = formatter.format(this.valueInFiatCurrency);

        return formattedValue;
    }
}
