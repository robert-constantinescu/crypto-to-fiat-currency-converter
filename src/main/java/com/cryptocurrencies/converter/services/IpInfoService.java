package com.cryptocurrencies.converter.services;

import com.cryptocurrencies.converter.controller.dto.IpInfoDTO;
import com.cryptocurrencies.converter.utils.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.cryptocurrencies.converter.utils.Constants.*;

@Service
public class IpInfoService {

    private IpInfoDTO defaultInfoDto;

    private final RestTemplate restTemplate;

    public IpInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IpInfoDTO getInfoForIp(String ip) {
        String url = String.format(URL_INFO_FROM_IP, ip);

        if ( !isValidIp(ip)) {
            return getDefaultIpInfoDto();
        }

        IpInfoDTO ipInfoDTO = makeApiCall(url);
        if (ipInfoDTO.hasError()) {
            return getDefaultIpInfoDto();
        }

        return ipInfoDTO;
    }


    private IpInfoDTO makeApiCall(String url) {
        ResponseEntity<IpInfoDTO> response = restTemplate.getForEntity(url, IpInfoDTO.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return getDefaultIpInfoDto();
        }

        return response.getBody();
    }

    private boolean isValidIp(String ip) {
        if (ip == null) {
            return false;
        }

        String trimmedIp = ip.trim();
        boolean ipIsValid = Constants.PATTERN_VALID_IPV4.matcher(trimmedIp).matches();

        return ipIsValid;
    }

    private IpInfoDTO getDefaultIpInfoDto() {
        if (this.defaultInfoDto != null) {
            return this.defaultInfoDto;
        }

        IpInfoDTO defaultIpInfoDto = new IpInfoDTO();
        defaultIpInfoDto.setCountry(COUNTRY_UNITED_STATES);
        defaultIpInfoDto.setLanguages(LANGUAGE_ENGLISH);
        defaultIpInfoDto.setCurrency(CURRENCY_USD);
        this.defaultInfoDto = defaultIpInfoDto;

        return this.defaultInfoDto;
    }
}
