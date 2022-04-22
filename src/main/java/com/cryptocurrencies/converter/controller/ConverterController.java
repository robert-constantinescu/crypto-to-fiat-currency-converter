package com.cryptocurrencies.converter.controller;


import com.cryptocurrencies.converter.controller.dto.CoinMarketCapCryptocurrenciesResponse;
import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.model.Cryptocurrency;
import com.cryptocurrencies.converter.services.ConverterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import static com.cryptocurrencies.converter.utils.PagesConstants.PAGE_HOME;

@Controller
public class ConverterController {

    private static List<Cryptocurrency> cryptocurrencyList = List.of();
    private final ConverterService converterService;

    public ConverterController(ConverterService converterService) {
        this.converterService = converterService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("converter", new ConverterInfoDTO());
        return PAGE_HOME;
    }

    @ModelAttribute("cryptocurrencies")
    public Collection<Cryptocurrency> populateCryptocurrencies() throws IOException {

        if (cryptocurrencyList.isEmpty()) {
            cryptocurrencyList = readCryptocurrenciesFromJsonFile(Path.of("coinmarketcap.json"));
        }

        return cryptocurrencyList;
    }

    @PostMapping("/")
    public String convertCrypto(@ModelAttribute("converter") ConverterInfoDTO converterInfo, Model model) throws URISyntaxException, IOException {
        ConverterInfoDTO convert = converterService.convert(converterInfo);

        model.addAttribute("converter", converterInfo);
        return PAGE_HOME;
    }


    private List<Cryptocurrency> readCryptocurrenciesFromJsonFile(Path jsonPath) throws IOException {

        byte[] bytes = Files.readAllBytes(jsonPath);
        ObjectMapper mapper = new ObjectMapper();
        CoinMarketCapCryptocurrenciesResponse response = mapper.readValue(bytes, CoinMarketCapCryptocurrenciesResponse.class);

        return response.getData();
    }



}
