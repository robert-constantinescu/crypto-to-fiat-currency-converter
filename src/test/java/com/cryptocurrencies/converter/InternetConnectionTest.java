package com.cryptocurrencies.converter;


import com.cryptocurrencies.converter.config.CoinMarketCapProperties;
import com.cryptocurrencies.converter.services.CoinMarketCapService;
import com.cryptocurrencies.converter.services.IpInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@ExtendWith({MockitoExtension.class})
@SpringBootTest
public class InternetConnectionTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void shouldConnectToApiAndReceiveAnswer() {
        String URL_BASE = "https://sandbox-api.coinmarketcap.com/v1/tools/price-conversion?amount=1&symbol=BTC&convert=USD";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("X-CMC_PRO_API_KEY", "the_sandbox_does_not_care");

        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(URL_BASE, HttpMethod.GET, entity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
