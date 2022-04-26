package com.cryptocurrencies.converter.controller;

import com.cryptocurrencies.converter.controller.dto.ConverterInfoDTO;
import com.cryptocurrencies.converter.controller.dto.QuoteResponseDTO;
import com.cryptocurrencies.converter.services.CoinMarketCapService;
import com.cryptocurrencies.converter.services.ConverterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

import static com.cryptocurrencies.converter.utils.Constants.CURRENCY_USD;
import static com.cryptocurrencies.converter.utils.PagesConstants.PAGE_HOME;

@ControllerAdvice
public class ConverterExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Logger LOG = LoggerFactory.getLogger(ConverterExceptionHandler.class);

    private final ConverterService converterService;
    private final CoinMarketCapService coinMarketCapService;

    private ObjectMapper mapper;

    public ConverterExceptionHandler(ConverterService converterService, CoinMarketCapService coinMarketCapService,
                                     ObjectMapper mapper) {
        this.converterService = converterService;
        this.coinMarketCapService = coinMarketCapService;
        this.mapper = mapper;
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    String handleException(HttpClientErrorException ex, HttpServletRequest request, Model model)  {
        ConverterInfoDTO converterInfoDTO = new ConverterInfoDTO();
        converterInfoDTO.setCryptoSymbol(request.getParameter("cryptoSymbol"));
        converterInfoDTO.setIpLocale(Locale.US);
        converterInfoDTO.setFiatCurrency(CURRENCY_USD);

        try {
            QuoteResponseDTO response = mapper.readValue(ex.getResponseBodyAsString(), QuoteResponseDTO.class);
            converterInfoDTO.setErrorMessage(response.getStatus().getErrorMessage());

            QuoteResponseDTO newRequest = coinMarketCapService.getCoinValue(converterInfoDTO);
            converterInfoDTO.setValueInFiatCurrency(newRequest.getData().getQuote().get(CURRENCY_USD).getPrice());

        } catch (IOException e) {
            LOG.error("Unexpected exception: {}", ex.getMessage());
        }

        LOG.error("handling exception in: {}", ConverterExceptionHandler.class);
        model.addAttribute("cryptocurrencies", converterService.getAllCryptocurrenciesSortedByRank());
        model.addAttribute("converter", converterInfoDTO);
        model.addAttribute("error", converterInfoDTO.getErrorMessage());

        return PAGE_HOME;
    }



}

