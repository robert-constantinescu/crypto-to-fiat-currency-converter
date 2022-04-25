package com.cryptocurrencies.converter.controller.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class ConverterInfoDTOTest {


    @Test
    void shouldReturnTheProperFormattingBasedOnConverterInfoLocale() {

        ConverterInfoDTO mockObject = new ConverterInfoDTO();
        mockObject.setValueInFiatCurrency(133.3124d);
        mockObject.setIpLocale(Locale.JAPAN);

        String formattedValue = mockObject.displayFiatCurrencyValueInLocalFormat();
        Assertions.assertThrows(
                NumberFormatException.class,
                () -> Double.parseDouble(formattedValue)
        );
    }
}
