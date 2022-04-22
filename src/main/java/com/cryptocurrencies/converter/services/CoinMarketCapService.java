package com.cryptocurrencies.converter.services;

import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoinMarketCapService {

    private static String apiKey = "87785e5f-a340-4ab4-9d34-2465689ed904";

//    String baseUrl = "https://sandbox-api.coinmarketcap.com";
    String baseUrl = "https://pro-api.coinmarketcap.com";

    String allCurrencies = "/v1/cryptocurrency/map";
    String quotes = "/v1/tools/price-conversion";

    private final RestTemplate restTemplate;

    public CoinMarketCapService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    public String getCoinValue(ConverterInfoDTO converterInfoDTO) throws URISyntaxException, IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("X-CMC_PRO_API_KEY", apiKey);

        HttpEntity entity = new HttpEntity(httpHeaders);

        List<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("amount", "1"));
        queryParameters.add(new BasicNameValuePair("id", converterInfoDTO.getCoinMarketCapId()));
        queryParameters.add(new BasicNameValuePair("convert", converterInfoDTO.getCurrency()));

        String url = baseUrl + quotes;
        URIBuilder query = new URIBuilder(url);
        query.addParameters(queryParameters);

        URI finalUrl = query.build();

        ResponseEntity<String> exchange = restTemplate.exchange(finalUrl, HttpMethod.GET, entity, String.class);
        System.out.println(exchange);

        Files.writeString(Path.of("/price_response.json"), exchange.getBody());

        return exchange.getBody();
    }



}
