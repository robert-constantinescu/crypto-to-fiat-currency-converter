package com.cryptocurrencies.converter.controller;


import com.cryptocurrencies.converter.model.Cryptocurrency;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.List;

@Controller
public class ConverterController {

    private final static String HOME_PAGE = "home";

    Cryptocurrency ADA = new Cryptocurrency("ADA", "Cardano");
    Cryptocurrency TRX = new Cryptocurrency("TRX", "Tron");

    @GetMapping("/")
    public String homePage() {
        return HOME_PAGE;
    }

    @ModelAttribute("cryptocurrencies")
    public Collection<Cryptocurrency> populateCryptocurrencies() {
        List<Cryptocurrency> cryptocurrencyList = List.of(ADA, TRX);

        return cryptocurrencyList;
    }


}
