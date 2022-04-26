package com.cryptocurrencies.converter.controller.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpInfoDTO {

    private String country;
    private String languages;
    private String currency;
    private boolean error = false;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isError() {
        return error;
    }

    public boolean hasError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getLanguageAndCountry(){
        String firstLanguage = languages.split(",")[0];
        boolean isLanguageWithCountry = firstLanguage.length() > 3;
        if (isLanguageWithCountry) {
            return firstLanguage;
        }

        String languageWithCountry = firstLanguage + "-" + country;
        return languageWithCountry;
    }

    @Override
    public String toString() {
        return "IpInfoDTO{" +
                "country='" + country + '\'' +
                ", languages='" + languages + '\'' +
                ", currency='" + currency + '\'' +
                ", error=" + error +
                '}';
    }
}
