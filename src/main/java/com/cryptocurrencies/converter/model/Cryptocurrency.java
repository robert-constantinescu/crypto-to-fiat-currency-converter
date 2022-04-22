package com.cryptocurrencies.converter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cryptocurrency {

    private Long id;
    private String symbol;
    private String name;
    private String slug;
    private Long rank;
    private BigDecimal valueInUsd;

    public Cryptocurrency() {
    }

    public Cryptocurrency(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }


    @Override
    public String toString() {
        return "Cryptocurrency{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", rank=" + rank +
                ", valueInUsd=" + valueInUsd +
                '}';
    }
}
