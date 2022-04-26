package com.cryptocurrencies.converter.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusDTO {

    @JsonProperty("credit_count")
    private Long creditCount;

    @JsonProperty("error_message")
    private String errorMessage;

    public Long getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(Long creditCount) {
        this.creditCount = creditCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
