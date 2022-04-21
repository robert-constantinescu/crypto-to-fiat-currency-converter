package com.cryptocurrencies.converter.model;

public class ConverterInfoDTO {

    private String valueInUsd;
    private String ip;

    public String getValueInUsd() {
        return valueInUsd;
    }

    public void setValueInUsd(String valueInUsd) {
        this.valueInUsd = valueInUsd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "{" +
                "valueInUsd='" + valueInUsd + '\'' +
                ", ip='" + ip + '\'' +
                ", objectClass='" + this.getClass().getSimpleName() + '\'' +
                "}";
    }
}
