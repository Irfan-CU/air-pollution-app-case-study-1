package com.cgiair.airqualityinfo.Model.v2.AirQuality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AirQualityV2 {

private DataV2 data;

    public AirQualityV2(DataV2 data) {
        this.data = data;
    }

    public AirQualityV2() {
    }

    public DataV2 getData() {
        return data;
    }

    public void setData(DataV2 data) {
        this.data = data;
    }
}
