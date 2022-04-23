package com.cryptocurrencies.converter.service;

import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.controller.dto.DataDTO;
import com.cryptocurrencies.converter.controller.dto.PriceDTO;
import com.cryptocurrencies.converter.controller.dto.QuoteResponseDTO;
import com.cryptocurrencies.converter.services.CoinMarketCapService;
import com.cryptocurrencies.converter.services.ConverterService;
import com.cryptocurrencies.converter.services.CurrencyFromIpService;
import com.cryptocurrencies.converter.utils.CustomObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static com.cryptocurrencies.converter.utils.Constants.CURRENCY_USD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ConverterServiceTest {

    @InjectMocks
    private ConverterService converterService;

    @Mock
    private CurrencyFromIpService currencyFromIpService;

    @Mock
    private CoinMarketCapService coinMarketCapService;

    @Mock
    private CustomObjectMapper mapper;


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

        Mockito.when(currencyFromIpService.getCurrency(ip)).thenReturn(CURRENCY_USD);
        Mockito.when(coinMarketCapService.getCoinValue(converterInfoDTO)).thenReturn(quoteResponseDTO);

        converterService.convert(converterInfoDTO);
        Mockito.verify(currencyFromIpService, times(1)).getCurrency(any());
        Mockito.verify(coinMarketCapService, times(1)).getCoinValue(any());

        Assertions.assertEquals(CURRENCY_USD, converterInfoDTO.getCurrency());
        Assertions.assertEquals(mockPrice, converterInfoDTO.getValueInCurrency());
    }


    @Test
    void shouldReturnUsdIfIpIsInvalid() {
        String ip = "168.0.1";
        String fiatCurrency = converterService.getFiatCurrency(ip);
        Assertions.assertEquals("USD", fiatCurrency);
    }

    @Test
    void shouldGetIpFromServiceIfIpIsValid() {
        String ip = "192.168.0.1";
        String fiatCurrency = converterService.getFiatCurrency(ip);

        Mockito.verify(currencyFromIpService, times(1)).getCurrency(any());
    }


}
