package com.cgiair.model;

public class FavoriteItem {
    private String country;
    private String state;
    private String city;

    public FavoriteItem(String country, String state, String city) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public FavoriteItem() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "FavoriteItem{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
