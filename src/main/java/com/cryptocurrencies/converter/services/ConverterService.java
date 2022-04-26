package com.cryptocurrencies.converter.services;

import com.cryptocurrencies.converter.controller.dto.*;
import com.cryptocurrencies.converter.model.Cryptocurrency;
import com.cryptocurrencies.converter.repository.CryptocurrencyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ConverterService {

    private final static Logger LOG = LoggerFactory.getLogger(ConverterService.class);

    private final CoinMarketCapService coinMarketCapService;
    private final IpInfoService ipInfoService;
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final ObjectMapper mapper;

    public ConverterService(CoinMarketCapService coinMarketCapService, IpInfoService ipInfoService,
                            CryptocurrencyRepository cryptocurrencyRepository, ObjectMapper mapper) {
        this.coinMarketCapService = coinMarketCapService;
        this.ipInfoService = ipInfoService;
        this.cryptocurrencyRepository = cryptocurrencyRepository;
        this.mapper = mapper;
    }

    public ConverterInfoDTO convert(ConverterInfoDTO converterInfo) {
        IpInfoDTO infoForIp = ipInfoService.getInfoForIp(converterInfo.getIp());

        Locale ipLocale = Locale.forLanguageTag(infoForIp.getLanguageAndCountry());
        converterInfo.setIpLocale(ipLocale);

        String localCurrencyCode = Currency.getInstance(ipLocale).getCurrencyCode();
        converterInfo.setFiatCurrency(localCurrencyCode);

        QuoteResponseDTO quoteResponse = coinMarketCapService.getCoinValue(converterInfo);
        PriceDTO priceDTO = quoteResponse.getData().getQuote().get(converterInfo.getFiatCurrency());
        converterInfo.setValueInFiatCurrency(priceDTO.getPrice());

        return converterInfo;
    }

    public List<Cryptocurrency> getAllCryptocurrenciesSortedByRank() {
        List<Cryptocurrency> all = cryptocurrencyRepository.findAll();

        if (all.size() == 0) {
            //backup, in case from some reason the DB can't be read
            LOG.warn("Couldn't retrieve data from H2 DB. Reading from the Json file");
            all = readCryptocurrenciesFromJsonFile(Path.of("coinmarketcap.json"));
        }

        List<Cryptocurrency> sortedCrypto = all.stream()
                .sorted(Comparator.comparing(Cryptocurrency::getRank))
                .collect(Collectors.toList());

        return sortedCrypto;
    }


    //not used anymore, let it here in case i want to read again the json file with all currencies
    private List<Cryptocurrency> readCryptocurrenciesFromJsonFile(Path jsonPath) {
        List<Cryptocurrency> allCryptos = List.of();
        try {
            byte[] bytes = Files.readAllBytes(jsonPath);
            CoinMarketCapCryptocurrenciesResponse response = mapper.readValue(bytes, CoinMarketCapCryptocurrenciesResponse.class);
            allCryptos = response.getData();
        } catch (IOException e) {
            LOG.error("Couldn't read the Crypto files. Reason: {}", e.getMessage());
        }
//        cryptocurrencyRepository.saveAll(allCryptos);
        return allCryptos;
    }
}
