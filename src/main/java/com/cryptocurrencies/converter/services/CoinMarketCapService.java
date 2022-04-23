package com.cryptocurrencies.converter.services;

import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.controller.dto.QuoteResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoinMarketCapService {

    private final static Logger LOG = LoggerFactory.getLogger(CoinMarketCapService.class);
    private static final String API_KEY = "87785e5f-a340-4ab4-9d34-2465689ed904";
    private static final String AMOUNT_TO_CONVERT = "1";

//    String URL_BASE = "https://sandbox-api.coinmarketcap.com";
    private static final String URL_BASE = "https://pro-api.coinmarketcap.com";

    private static final String API_ALL_CURRENCIES = "/v1/cryptocurrency/map";
    private static final String API_QUOTES = "/v1/tools/price-conversion";

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public CoinMarketCapService(
            @Qualifier("customMapper") ObjectMapper mapper,
            RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }


    public QuoteResponseDTO getCoinValue(ConverterInfoDTO converterInfoDTO) {
        List<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("amount", AMOUNT_TO_CONVERT));
        queryParameters.add(new BasicNameValuePair("symbol", converterInfoDTO.getCryptoSymbol()));
        queryParameters.add(new BasicNameValuePair("convert", converterInfoDTO.getCurrency()));

        String url = URL_BASE + API_QUOTES;

        String mockResponse = null;
        QuoteResponseDTO quoteResponseDTO = null;

        try {
            mockResponse = Files.readString(Path.of("src/test/java/com/cryptocurrencies/converter/data/quotes.json"));
            quoteResponseDTO = mapper.readValue(mockResponse, QuoteResponseDTO.class);
//
//            ResponseEntity<String> responseEntity = makeGetCall(url, queryParameters);
//            quoteResponseDTO = mapper.readValue(responseEntity.getBody(), QuoteResponseDTO.class);

        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }

        return quoteResponseDTO;
    }


    private ResponseEntity<String> makeGetCall(String url, List<NameValuePair> parameters) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("X-CMC_PRO_API_KEY", API_KEY);

        HttpEntity entity = new HttpEntity(httpHeaders);

        URI uri = URI.create(url);
        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        URI finalUrl = URI.create("");
        try {
            finalUrl = query.build();
        } catch (URISyntaxException ex) {
            LOG.error(ex.getMessage());
        }

        ResponseEntity<String> exchange = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, String.class);
        return exchange;
    }



}
