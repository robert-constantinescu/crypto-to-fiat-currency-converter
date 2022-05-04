package com.cryptocurrencies.converter.services.rest;

import com.cryptocurrencies.converter.repository.CryptocurrencyMongoRepository;
import org.springframework.stereotype.Service;

@Service
public class RestConverterService {


    private CryptocurrencyMongoRepository repository;

    public RestConverterService(CryptocurrencyMongoRepository repository) {
        this.repository = repository;
    }
}
