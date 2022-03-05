package com.cgiair.airqualityinfo.Service.v2;


import com.cgiair.airqualityinfo.Model.v2.AirQuality.DataListV2;
import com.cgiair.airqualityinfo.Service.AirQualityInfoFuncs;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class AirQualityInfoImplV2 implements AirQualityInfoV2 {

    private AirQualityInfoFuncs airQualityInfoFuncs;

    @Autowired
    public AirQualityInfoImplV2(AirQualityInfoFuncs airQualityInfoFuncs) {
        this.airQualityInfoFuncs = airQualityInfoFuncs;
    }

    @Override
    public DataListV2 getAirQualityData(String Country, String City) throws IOException {
        String url = "http://api.weatherbit.io/v2.0/history/airquality?" +
                "&city=+"+City+
                "&country=US" +
                "&key=a34d4bae02634369971f15c320557fd8&include=minutely";

        JsonObject rootobj = airQualityInfoFuncs.jsonObjectImplementation(url);
        JsonArray temp = new JsonArray();
        String tempi = rootobj.get("data").getAsJsonArray().get(71).toString();
        Gson gson = new Gson();
        DataListV2 dataV2 = gson.fromJson(tempi,DataListV2.class);
        return dataV2;
    }
}

