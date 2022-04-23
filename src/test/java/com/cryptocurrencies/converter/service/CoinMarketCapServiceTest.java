package com.cryptocurrencies.converter.service;

import com.cryptocurrencies.converter.CryptoConverterApplication;
import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.controller.dto.DataDTO;
import com.cryptocurrencies.converter.controller.dto.PriceDTO;
import com.cryptocurrencies.converter.controller.dto.QuoteResponseDTO;
import com.cryptocurrencies.converter.services.CoinMarketCapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

import static com.cryptocurrencies.converter.utils.Constants.CURRENCY_USD;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
@SpringBootTest
public class CoinMarketCapServiceTest {

    @Autowired
    private CoinMarketCapService coinMarketCapService;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    void shouldConnectToCoinMarketCapApiAndGetFiatValue() throws IOException {
        ConverterInfoDTO converterInfoDTO = new ConverterInfoDTO();
        converterInfoDTO.setCryptoSymbol("BTC");
        converterInfoDTO.setCurrency(CURRENCY_USD);

        String body = Files.readString(Path.of("src/test/java/com/cryptocurrencies/converter/data/quotes.json"));
        Mockito
                .when(restTemplate.exchange(any(), any(), any(), eq(String.class)))
                .thenReturn(ResponseEntity.of(Optional.of(body)));

        QuoteResponseDTO coinValue = coinMarketCapService.getCoinValue(converterInfoDTO);

        URI expectedUri = URI.create("https://pro-api.coinmarketcap.com/v1/tools/price-conversion?amount=1&symbol=BTC&convert=USD");
       verify(restTemplate, Mockito.times(1))
                .exchange(
                        eq(expectedUri),
                        eq(HttpMethod.GET),
                        any(),
                        eq(String.class));

        DataDTO receivedData = coinValue.getData();
        Map<String, PriceDTO> quote = receivedData.getQuote();

        Assertions.assertEquals("BTC", receivedData.getSymbol());
        Assertions.assertTrue( quote.containsKey(CURRENCY_USD));
        Assertions.assertEquals(1, quote.size());

        PriceDTO priceDTO = quote.get(CURRENCY_USD);
        Assertions.assertEquals(181084.3532891763, priceDTO.getPrice());
    }

}
