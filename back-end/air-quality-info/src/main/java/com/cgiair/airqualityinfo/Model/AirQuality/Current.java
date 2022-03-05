package com.cgiair.airqualityinfo.Model.AirQuality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "weather")
public class Current {

    private Pollution pollution;

    public Current(Pollution pollution) {
        this.pollution = pollution;
    }

    public Current() {
    }

    public Pollution getPollution() {
        return pollution;
    }

    public void setPollution(Pollution pollution) {
        this.pollution = pollution;
    }
}
