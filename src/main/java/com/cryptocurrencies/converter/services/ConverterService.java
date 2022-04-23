package com.cryptocurrencies.converter.services;

import com.cryptocurrencies.converter.controller.dto.CoinMarketCapCryptocurrenciesResponse;
import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.controller.dto.PriceDTO;
import com.cryptocurrencies.converter.controller.dto.QuoteResponseDTO;
import com.cryptocurrencies.converter.model.Cryptocurrency;
import com.cryptocurrencies.converter.utils.Constants;
import com.cryptocurrencies.converter.utils.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.cryptocurrencies.converter.utils.Constants.CURRENCY_USD;
import static com.cryptocurrencies.converter.utils.Constants.URL_CURRENCY_FROM_IP;

@Service
public class ConverterService {

    private final static Logger LOG = LoggerFactory.getLogger(ConverterService.class);

    private final RestTemplate restTemplate;
    private final CoinMarketCapService coinMarketCapService;
    private final ObjectMapper mapper;

    public ConverterService(RestTemplateBuilder restTemplateBuilder,
                            CoinMarketCapService coinMarketCapService,
                            CustomObjectMapper mapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.coinMarketCapService = coinMarketCapService;
        this.mapper = mapper.getCustomMapper();
    }

    public ConverterInfoDTO convert(ConverterInfoDTO converterInfo) throws URISyntaxException, IOException {
        String currency = getFiatCurrency(converterInfo.getIp());
        converterInfo.setCurrency(currency);

        QuoteResponseDTO quoteResponse = coinMarketCapService.getCoinValue(converterInfo);
        PriceDTO priceDTO = quoteResponse.getData().getQuote().get(currency);
        converterInfo.setValueInCurrency(priceDTO.getPrice());

        return converterInfo;
    }

    public List<Cryptocurrency> readCryptocurrenciesFromJsonFile(Path jsonPath) throws IOException {
        byte[] bytes = Files.readAllBytes(jsonPath);
        CoinMarketCapCryptocurrenciesResponse response = mapper.readValue(bytes, CoinMarketCapCryptocurrenciesResponse.class);

        return response.getData();
    }

    private String getFiatCurrency(String ip) {
        String currency = CURRENCY_USD;
        String trimmedIp = ip.trim();

        boolean ipIsValid = Constants.PATTERN_VALID_IPV4.matcher(trimmedIp).matches();
        if (ipIsValid) {
            currency = getFiatCurrencyService(ip);
        }

        return currency;
    }

    private String getFiatCurrencyService(String ip) {
        String url = String.format(URL_CURRENCY_FROM_IP, ip);
        ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);
        String ipLocalCurrency = response.getBody();
        return ipLocalCurrency;
    }

}
