package com.cryptocurrencies.converter.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.cryptocurrencies.converter.utils.Constants.CURRENCY_USD;
import static com.cryptocurrencies.converter.utils.Constants.URL_CURRENCY_FROM_IP;

@Service
public class CurrencyFromIpService {

    private final RestTemplate restTemplate;

    public CurrencyFromIpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCurrency(String ip) {
        String url = String.format(URL_CURRENCY_FROM_IP, ip);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String ipLocalCurrency = response.getBody();

        assert ipLocalCurrency != null;
        if ("Undefined".matches(ipLocalCurrency)) {
            ipLocalCurrency = CURRENCY_USD;
        }

        return ipLocalCurrency;
    }
}
