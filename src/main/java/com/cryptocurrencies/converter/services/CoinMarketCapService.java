package com.cryptocurrencies.converter.services;

import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.controller.dto.QuoteResponseDTO;
import com.cryptocurrencies.converter.utils.CustomObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
    private static String API_KEY = "87785e5f-a340-4ab4-9d34-2465689ed904";

//    String URL_BASE = "https://sandbox-api.coinmarketcap.com";
    private final String URL_BASE = "https://pro-api.coinmarketcap.com";

    private final String API_ALL_CURRENCIES = "/v1/cryptocurrency/map";
    private final String API_QUOTES = "/v1/tools/price-conversion";


    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public CoinMarketCapService(CustomObjectMapper mapper, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.mapper = mapper.getCustomMapper();
    }


    public QuoteResponseDTO getCoinValue(ConverterInfoDTO converterInfoDTO) throws URISyntaxException, IOException {
        List<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("amount", "1"));
        queryParameters.add(new BasicNameValuePair("id", converterInfoDTO.getCoinMarketCapId()));
        queryParameters.add(new BasicNameValuePair("convert", converterInfoDTO.getCurrency()));

        String url = URL_BASE + API_QUOTES;

//        ResponseEntity<String> responseEntity = makeGetCall(url, queryParameters);
//        QuoteResponseDTO quoteResponseDTO = mapper.readValue(responseEntity.getBody(), QuoteResponseDTO.class);

        String mockResponse = Files.readString(Path.of("quotes.json"));
        QuoteResponseDTO quoteResponseDTO = mapper.readValue(mockResponse, QuoteResponseDTO.class);

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
