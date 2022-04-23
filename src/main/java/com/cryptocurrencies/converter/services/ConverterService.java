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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    private final CoinMarketCapService coinMarketCapService;
    private final CurrencyFromIpService currencyFromIpService;
    private final ObjectMapper mapper;

    public ConverterService(CoinMarketCapService coinMarketCapService,
                            CurrencyFromIpService currencyFromIpService, CustomObjectMapper mapper) {
        this.coinMarketCapService = coinMarketCapService;
        this.currencyFromIpService = currencyFromIpService;
        this.mapper = mapper.getCustomMapper();
    }

    public ConverterInfoDTO convert(ConverterInfoDTO converterInfo) {
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

    public String  getFiatCurrency(String ip) {
        String currency = CURRENCY_USD;
        String trimmedIp = ip.trim();

        boolean ipIsValid = Constants.PATTERN_VALID_IPV4.matcher(trimmedIp).matches();
        if (ipIsValid) {
            currency = currencyFromIpService.getCurrency(ip);
        }
        return currency;
    }



}
