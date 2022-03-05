package com.cgiair.airqualityinfo.Service.v2;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQuality;
import com.cgiair.airqualityinfo.Model.v2.AirQuality.DataListV2;

import java.io.IOException;

public interface AirQualityInfoV2 {

        DataListV2 getAirQualityData(String Country, String City) throws IOException;
}
