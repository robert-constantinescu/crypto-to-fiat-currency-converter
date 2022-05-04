package com.cryptocurrencies.converter.controller.rest;

import com.cryptocurrencies.converter.services.CoinMarketCapService;
import com.cryptocurrencies.converter.services.rest.RestConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping(value = "/api/v1")
public class RestConverterController {

    private final static Logger LOG = LoggerFactory.getLogger(RestConverterController.class);
    private RestConverterService converterService;

    public RestConverterController(RestConverterService converterService) {
        this.converterService = converterService;
    }


    @GetMapping
    public ResponseEntity<String> convertValue() {

        return ResponseEntity.ok("This endpoint works");
    }
}
