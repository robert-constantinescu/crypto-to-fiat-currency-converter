package com.cryptocurrencies.converter.controller.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDTO {

    private long id;
    private String symbol;

    @JsonProperty("last_updated")
    private LocalDateTime lastUpdated;

    private Map<String, PriceDTO> quote;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Map<String, PriceDTO> getQuote() {
        return quote;
    }

    public void setQuote(Map<String, PriceDTO> quote) {
        this.quote = quote;
    }
}
