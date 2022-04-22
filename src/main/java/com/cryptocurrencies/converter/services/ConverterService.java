package com.cryptocurrencies.converter.services;

import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.controller.dto.PriceDTO;
import com.cryptocurrencies.converter.controller.dto.QuoteResponseDTO;
import com.cryptocurrencies.converter.utils.Constants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static com.cryptocurrencies.converter.utils.Constants.CURRENCY_USD;
import static com.cryptocurrencies.converter.utils.Constants.URL_CURRENCY_FROM_IP;

@Service
public class ConverterService {

    private final static Logger LOG = Logger.getLogger(ConverterService.class.getSimpleName());

    private final RestTemplate restTemplate;
    private final CoinMarketCapService coinMarketCapService;

    public ConverterService(RestTemplateBuilder restTemplateBuilder, CoinMarketCapService coinMarketCapService) {
        this.restTemplate = restTemplateBuilder.build();
        this.coinMarketCapService = coinMarketCapService;
    }

    public ConverterInfoDTO convert(ConverterInfoDTO converterInfo) throws URISyntaxException, IOException {
        String currency = getCurrency(converterInfo.getIp());
        converterInfo.setCurrency(currency);

        QuoteResponseDTO quoteResponse = coinMarketCapService.getCoinValue(converterInfo);
        PriceDTO price = quoteResponse.getData().getQuote().get("RON");
        converterInfo.setValueInCurrency(price.getPrice());

        return converterInfo;
    }

    private String getCurrency(String ip) {
        String currency = CURRENCY_USD;

        boolean ipIsValid = Constants.PATTERN_VALID_IPV4.matcher(ip).matches();
        if (ipIsValid) {
            currency = getCurrencyService(ip);
        }

        return currency;
    }

    private String getCurrencyService(String ip) {
        String url = String.format(URL_CURRENCY_FROM_IP, ip);
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        String ipLocalCurrency = response.getBody();
        return ipLocalCurrency;
    }

}
