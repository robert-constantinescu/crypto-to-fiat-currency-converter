package com.cryptocurrencies.converter.controller.dto;

import java.text.NumberFormat;
import java.util.Locale;

public class ConverterInfoDTO {

    private String cryptoSymbol;
    private String ip;
    private String fiatCurrency;
    private Double valueInFiatCurrency;
    private Locale ipLocale;

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

    public String displayFiatCurrencyValueInLocalFormat() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(this.ipLocale);
        String formattedValue = formatter.format(this.valueInFiatCurrency);

        return formattedValue;
    }
}
