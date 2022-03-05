package com.cgiair.airqualityinfo.Model.Country;

import java.io.Serializable;

public class Data implements Serializable {

    private Country[] countryArray;

    public Data(Country[] countryArray) {
        this.countryArray = countryArray;
    }

    public Data() {
    }

    public Country[] getCountryArray() {
        return countryArray;
    }

    public void setCountryArray(Country[] countryArray) {
        this.countryArray = countryArray;
    }
}
