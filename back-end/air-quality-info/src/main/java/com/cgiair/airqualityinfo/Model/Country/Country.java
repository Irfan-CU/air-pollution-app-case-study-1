package com.cgiair.airqualityinfo.Model.Country;

import java.io.Serializable;
import java.util.List;

public class Country implements Serializable {

    private List<CountryMicro> Country;

    public Country(List<CountryMicro> country) {
        Country = country;
    }

    public Country() {
    }

    public List<CountryMicro> getCountry() {
        return Country;
    }

    public void setCountry(List<CountryMicro> country) {
        Country = country;
    }
}
