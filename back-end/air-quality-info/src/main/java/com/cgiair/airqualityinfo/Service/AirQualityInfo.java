package com.cgiair.airqualityinfo.Service;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQuality;
import com.cgiair.airqualityinfo.Model.AirQuality.AirQualityList;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;

public interface AirQualityInfo {
    AirQuality getAirQualityData(String Country,String State,String City) throws JsonProcessingException;
    AirQualityList getAirQualityDataForFavCities(String token);
    List<String> getAllSupportedCountries() throws IOException;
    List<String> getAllSupportedStates(String country) throws IOException;
    List<String> getAllSupportedCities(String country, String state) throws IOException;
    List<String> getNearestCityDataByIP();
    List<String> getNearestCityDataByGPS(String latitude, String longitude);

}
