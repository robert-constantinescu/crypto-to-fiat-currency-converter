package com.cryptocurrencies.converter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@JsonIgnoreProperties(value={ "id" }, ignoreUnknown = true)

@Entity
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_id;

    @Column
    private String symbol;

    @Column
    private String name;

    @Column
    private String slug;

    @Column
    private Long rank;

    @Column(name = "value_in_usd")
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
        return pk_id;
    }

    public void setId(Long id) {
        this.pk_id = id;
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
                "id=" + pk_id +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", rank=" + rank +
                ", valueInUsd=" + valueInUsd +
                '}';
    }
}
