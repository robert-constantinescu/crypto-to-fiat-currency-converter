package com.cryptocurrencies.converter.controller;


import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.model.Cryptocurrency;
import com.cryptocurrencies.converter.services.ConverterService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;

import static com.cryptocurrencies.converter.utils.PagesConstants.PAGE_ERROR;
import static com.cryptocurrencies.converter.utils.PagesConstants.PAGE_HOME;

@Controller
public class ConverterController implements ErrorController {

    private final ConverterService converterService;

    public ConverterController(ConverterService converterService) {
        this.converterService = converterService;
    }

    @RequestMapping(value = "/error")
    public String error() {
        return PAGE_ERROR;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        ConverterInfoDTO converterInfoDTO = new ConverterInfoDTO();
        model.addAttribute("converter", converterInfoDTO);
        return PAGE_HOME;
    }

    @ModelAttribute("cryptocurrencies")
    public Collection<Cryptocurrency> populateCryptocurrencies() {
        return converterService.getAllCryptocurrenciesSortedByRank();
    }

    @PostMapping("/")
    public String convertCrypto(@ModelAttribute("converter") ConverterInfoDTO converterInfo, Model model, HttpServletRequest request)  {
        String requestIp = request.getRemoteAddr();
        if (Objects.equals(converterInfo.getIp(), "")) {
            converterInfo.setIp(requestIp);
        }
        ConverterInfoDTO infoDTO = converterService.convert(converterInfo);
        model.addAttribute("converter", infoDTO);
        return PAGE_HOME;
    }

}
