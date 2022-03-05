package com.cgiair.airqualityinfo.Model.Country;

import com.cgiair.airqualityinfo.Model.Country.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

public class CountryList implements Serializable {
    private String status;
    private Data data;

    public CountryList() {
    }

    public CountryList(String status, Data data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
