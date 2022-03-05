package com.cgiair.airqualityinfo.Model.AirQuality;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQuality;

import java.util.ArrayList;
import java.util.List;

public class AirQualityList {
    private List<AirQuality> AirQualityList;

    public AirQualityList() {
        AirQualityList = new ArrayList<AirQuality>();
    }

    public AirQualityList(List<AirQuality> airQualityList) {
        AirQualityList = airQualityList;
    }

    public void add(AirQuality item){

        this.AirQualityList.add(item);
    }

    public List<AirQuality> getAirQualityList() {
        return AirQualityList;
    }

    public void setAirQualityList(List<AirQuality> airQualityList) {
        AirQualityList = airQualityList;
    }
}
