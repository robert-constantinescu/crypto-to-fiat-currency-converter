package com.cryptocurrencies.converter.controller;


import com.cryptocurrencies.converter.model.ConverterInfoDTO;
import com.cryptocurrencies.converter.model.Cryptocurrency;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Controller
public class ConverterController {

    private final static String HOME_PAGE = "home";

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("converter", new ConverterInfoDTO());
        return HOME_PAGE;
    }

    @ModelAttribute("cryptocurrencies")
    public Collection<Cryptocurrency> populateCryptocurrencies() {
        Cryptocurrency ada = new Cryptocurrency("ADA", "Cardano");
        Cryptocurrency trx = new Cryptocurrency("TRX", "Tron");
        ada.setValueInUsd(BigDecimal.valueOf(12.53110));
        trx.setValueInUsd(BigDecimal.valueOf(100.9857));

        List<Cryptocurrency> cryptocurrencyList = List.of(ada, trx);


        return cryptocurrencyList;
    }

    @PostMapping("/")
    public String theOnChangeMethod(@ModelAttribute("converter") ConverterInfoDTO converter, Model model) {
        System.out.println("Selected thicker is: " + converter);
        System.out.println("SModel: " + model);
        model.addAttribute("converter", converter);
        return HOME_PAGE;
    }




}
