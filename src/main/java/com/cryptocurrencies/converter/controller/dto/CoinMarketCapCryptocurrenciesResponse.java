package com.cryptocurrencies.converter.controller.dto;

import com.cryptocurrencies.converter.model.Cryptocurrency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinMarketCapCryptocurrenciesResponse {

    private List<Cryptocurrency> data;

    public List<Cryptocurrency> getData() {
        return data;
    }

    public void setData(List<Cryptocurrency> data) {
        this.data = data;
    }
}
