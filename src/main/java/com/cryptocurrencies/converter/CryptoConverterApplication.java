package com.cryptocurrencies.converter;

import com.cryptocurrencies.converter.repository.CryptocurrencyMongoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@EnableMongoRepositories(basePackageClasses = CryptocurrencyMongoRepository.class)
@EnableJpaRepositories(excludeFilters =
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CryptocurrencyMongoRepository.class))
@SpringBootApplication
public class CryptoConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoConverterApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    @Primary
    public ObjectMapper customMapper() {
        SimpleDateFormat dateFormatWithMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        ObjectMapper mapper = Jackson2ObjectMapperBuilder
                .json()
                .dateFormat(dateFormatWithMillis)
                .build();
        return mapper;
    }
}
