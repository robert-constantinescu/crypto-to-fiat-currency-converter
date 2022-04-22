package com.cryptocurrencies.converter.utils;

import java.util.regex.Pattern;

public class Constants {

    public final static Pattern PATTERN_VALID_IPV4 = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
    public final static String CURRENCY_USD = "USD";


    public static String URL_CURRENCY_FROM_IP = "https://ipapi.co/%s/currency/";


}
