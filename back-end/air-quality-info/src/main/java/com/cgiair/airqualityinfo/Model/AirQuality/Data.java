package com.cgiair.airqualityinfo.Model.AirQuality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "location")
public class Data {

    private String city;
    private String state;
    private String country;
    private Current current;
    private Location location;

    public Data(String city, String state, String country, Current current) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.current = current;

    }

    public Data() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
