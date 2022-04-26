package com.cryptocurrencies.converter.service;

import com.cryptocurrencies.converter.controller.dto.*;
import com.cryptocurrencies.converter.repository.CryptocurrencyRepository;
import com.cryptocurrencies.converter.services.CoinMarketCapService;
import com.cryptocurrencies.converter.services.ConverterService;
import com.cryptocurrencies.converter.services.IpInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static com.cryptocurrencies.converter.utils.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ConverterServiceTest {

    @InjectMocks
    private ConverterService converterService;

    @Mock
    private IpInfoService ipInfoService;

    @Mock
    private CoinMarketCapService coinMarketCapService;

    @Mock
    private CryptocurrencyRepository cryptocurrencyRepository;


    @Test
    void shouldConvertCryptoToFiat() {
        String ip = "192.168.0.1";
        double mockPrice = 1.0d;

        Map<String, PriceDTO> prices = new HashMap<>();

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setPrice(mockPrice);
        prices.put(CURRENCY_USD, priceDTO);

        DataDTO dataDTO = new DataDTO();
        dataDTO.setQuote(prices);

        QuoteResponseDTO quoteResponseDTO = new QuoteResponseDTO();
        quoteResponseDTO.setData(dataDTO);


        ConverterInfoDTO converterInfoDTO = new ConverterInfoDTO();
        converterInfoDTO.setCryptoSymbol("ADA");
        converterInfoDTO.setIp(ip);

        IpInfoDTO mockIpInfoDto = new IpInfoDTO();
        mockIpInfoDto.setLanguages(LANGUAGE_ENGLISH);
        mockIpInfoDto.setCountry(COUNTRY_UNITED_STATES);
        mockIpInfoDto.setCurrency(CURRENCY_USD);

        Mockito.when(ipInfoService.getInfoForIp(ip)).thenReturn(mockIpInfoDto);
        Mockito.when(coinMarketCapService.getCoinValue(converterInfoDTO)).thenReturn(quoteResponseDTO);

        converterService.convert(converterInfoDTO);


        Mockito.verify(ipInfoService, times(1)).getInfoForIp(any());
        Mockito.verify(coinMarketCapService, times(1)).getCoinValue(any());

        Assertions.assertEquals(CURRENCY_USD, converterInfoDTO.getFiatCurrency());
        Assertions.assertEquals(mockPrice, converterInfoDTO.getValueInFiatCurrency());
    }

    void shouldGetAllCurrenciesFromRepository() {

    }
}
