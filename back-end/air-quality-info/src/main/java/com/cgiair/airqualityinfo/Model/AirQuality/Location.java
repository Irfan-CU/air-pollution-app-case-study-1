package com.cgiair.airqualityinfo.Model.AirQuality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value="coordinates")
public class Location {
    private String type;

}
