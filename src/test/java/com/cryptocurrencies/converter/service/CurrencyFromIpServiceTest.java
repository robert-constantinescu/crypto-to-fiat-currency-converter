package com.cryptocurrencies.converter.service;


import com.cryptocurrencies.converter.services.CurrencyFromIpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.cryptocurrencies.converter.utils.Constants.CURRENCY_USD;
import static com.cryptocurrencies.converter.utils.Constants.URL_CURRENCY_FROM_IP;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CurrencyFromIpServiceTest {


    @InjectMocks
    private CurrencyFromIpService currencyFromIpService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void shouldMakeACallForTheInsertedIpIfValid() {
        String ip = "192.168.0.1";

        String craftedUrl = String.format(URL_CURRENCY_FROM_IP, ip);

        ResponseEntity<String> response = ResponseEntity.of(Optional.of(CURRENCY_USD));
        Mockito.when(restTemplate.getForEntity(craftedUrl, String.class))
                .thenReturn(response);

        String currency = currencyFromIpService.getCurrency(ip);

        Assertions.assertEquals(CURRENCY_USD, currency);
        Mockito.verify(restTemplate, times(1))
                .getForEntity(craftedUrl, String.class);
    }


    @Test
    void shouldReturnUsdIfResponseIsNull() {
        String ip = "192.168.0.1";

        String craftedUrl = String.format(URL_CURRENCY_FROM_IP, ip);

        ResponseEntity<String> response = ResponseEntity.of(Optional.empty());
        Mockito.when(restTemplate.getForEntity(craftedUrl, String.class))
                .thenReturn(response);

        String currency = currencyFromIpService.getCurrency(ip);

        Assertions.assertEquals(CURRENCY_USD, currency);
    }

    @Test
    void shouldReturnUsdIfResponseIsUndefined() {
        String ip = "192.168.0.1";

        String craftedUrl = String.format(URL_CURRENCY_FROM_IP, ip);

        ResponseEntity<String> response = ResponseEntity.of(Optional.of("Undefined"));
        Mockito.when(restTemplate.getForEntity(craftedUrl, String.class))
                .thenReturn(response);

        String currency = currencyFromIpService.getCurrency(ip);

        Assertions.assertEquals(CURRENCY_USD, currency);
    }


}
