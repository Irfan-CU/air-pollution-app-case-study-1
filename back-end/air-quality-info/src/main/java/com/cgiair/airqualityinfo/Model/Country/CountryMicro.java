package com.cgiair.airqualityinfo.Model.Country;

public class CountryMicro {

    private String country;

    public CountryMicro(String countryName) {
        this.country = countryName;
    }

    public CountryMicro() {
    }

    public String getCountryName() {
        return country;
    }

    public void setCountryName(String countryName) {
        this.country = countryName;
    }
}
