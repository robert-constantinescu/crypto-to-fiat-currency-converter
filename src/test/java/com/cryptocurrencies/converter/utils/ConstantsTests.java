package com.cryptocurrencies.converter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConstantsTests {


    @Test
    void shouldReturnTrueIfIpFormatIsValid() {
        String ip = "192.168.0.1";
        boolean isValid = Constants.PATTERN_VALID_IPV4.matcher(ip).matches();
        Assertions.assertTrue(isValid);
    }

    @Test
    void shouldReturnTrueIfIpFormatIsValid2() {
        String ip = "0.0.0.0";
        boolean isValid = Constants.PATTERN_VALID_IPV4.matcher(ip).matches();
        Assertions.assertTrue(isValid);
    }

    @Test
    void shouldReturnFalseIfIpFormatIsInvalid() {
        String ip = "192. 168.0.1";
        boolean isValid = Constants.PATTERN_VALID_IPV4.matcher(ip).matches();
        Assertions.assertFalse(isValid);
    }

    @Test
    void shouldReturnDefaultValues() {
        String defaultLanguage = "en";
        String defaultCountry = "US";
        String defaultCurrency = "USD";
        Assertions.assertEquals(defaultLanguage, Constants.LANGUAGE_ENGLISH);
        Assertions.assertEquals(defaultCountry, Constants.COUNTRY_UNITED_STATES);
        Assertions.assertEquals(defaultCurrency, Constants.CURRENCY_USD);

    }
}
