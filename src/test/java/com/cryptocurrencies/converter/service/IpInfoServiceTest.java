package com.cryptocurrencies.converter.service;


import com.cryptocurrencies.converter.controller.dto.IpInfoDTO;
import com.cryptocurrencies.converter.services.IpInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.cryptocurrencies.converter.utils.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class IpInfoServiceTest {


    @InjectMocks
    private IpInfoService ipInfoService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void shouldMakeACallForTheInsertedIpIfValid() {
        String ip = "192.168.0.1";

        String craftedUrl = String.format(URL_INFO_FROM_IP, ip);

        ResponseEntity<IpInfoDTO> response = ResponseEntity.of(Optional.of(new IpInfoDTO()));
        Mockito.when(restTemplate.getForEntity(craftedUrl, IpInfoDTO.class))
                .thenReturn(response);

        ipInfoService.getInfoForIp(ip);

        Mockito.verify(restTemplate, times(1))
                .getForEntity(craftedUrl, IpInfoDTO.class);
    }


    @Test
    void shouldReturnUsdIfResponseIsNull() {
        String ip = "192.168.0.1";

        String craftedUrl = String.format(URL_INFO_FROM_IP, ip);

        ResponseEntity<IpInfoDTO> response = ResponseEntity.of(Optional.empty());
        Mockito.when(restTemplate.getForEntity(craftedUrl, IpInfoDTO.class))
                .thenReturn(response);

        IpInfoDTO infoDto = ipInfoService.getInfoForIp(ip);

        Assertions.assertEquals(CURRENCY_USD, infoDto.getCurrency());
        Assertions.assertEquals(LANGUAGE_ENGLISH, infoDto.getLanguages());
        Assertions.assertEquals(COUNTRY_UNITED_STATES, infoDto.getCountry());

        Mockito.verify(restTemplate, times(1))
                .getForEntity(craftedUrl, IpInfoDTO.class);
    }

    @Test
    void shouldReturnDefaultIpInfoDtoIfIpInvalid() {
        String ip = "192.168 .0.1";
        IpInfoDTO infoDto = ipInfoService.getInfoForIp(ip);

        Assertions.assertEquals(CURRENCY_USD, infoDto.getCurrency());
        Assertions.assertEquals(LANGUAGE_ENGLISH, infoDto.getLanguages());
        Assertions.assertEquals(COUNTRY_UNITED_STATES, infoDto.getCountry());

        Mockito.verify(restTemplate, times(0))
                .getForEntity(any(), any());
    }

    @Test
    void shouldReturnDefaultIpInfoDtoIfApiStatusIsNot200() {
        String ip = "192.168.0.1";

        String craftedUrl = String.format(URL_INFO_FROM_IP, ip);

        ResponseEntity<IpInfoDTO> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IpInfoDTO());
        Mockito.when(restTemplate.getForEntity(craftedUrl, IpInfoDTO.class))
                .thenReturn(response);

        IpInfoDTO infoDto = ipInfoService.getInfoForIp(ip);

        Assertions.assertEquals(CURRENCY_USD, infoDto.getCurrency());
        Assertions.assertEquals(LANGUAGE_ENGLISH, infoDto.getLanguages());
        Assertions.assertEquals(COUNTRY_UNITED_STATES, infoDto.getCountry());
        Mockito.verify(restTemplate, times(1))
                .getForEntity(craftedUrl, IpInfoDTO.class);
    }


    @Test
    void positiveTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String body = Files.readString(Path.of("src/test/java/com/cryptocurrencies/converter/data/ip_japan.json"));
        IpInfoDTO mockDto = mapper.readValue(body, IpInfoDTO.class);

        String ip = "192.168.0.1";
        String craftedUrl = String.format(URL_INFO_FROM_IP, ip);

        ResponseEntity<IpInfoDTO> response = ResponseEntity.status(HttpStatus.OK).body(mockDto);
        Mockito.when(restTemplate.getForEntity(craftedUrl, IpInfoDTO.class))
                .thenReturn(response);

        IpInfoDTO infoDto = ipInfoService.getInfoForIp(ip);

        Assertions.assertEquals("JPY", infoDto.getCurrency());
        Assertions.assertEquals("ja", infoDto.getLanguages());
        Assertions.assertEquals("JP", infoDto.getCountry());
        Mockito.verify(restTemplate, times(1))
                .getForEntity(craftedUrl, IpInfoDTO.class);
    }

}
