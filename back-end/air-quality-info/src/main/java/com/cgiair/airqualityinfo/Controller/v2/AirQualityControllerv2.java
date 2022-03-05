package com.cgiair.airqualityinfo.Controller.v2;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQuality;
import com.cgiair.airqualityinfo.Model.v2.AirQuality.DataListV2;
import com.cgiair.airqualityinfo.Service.AirQualityInfo;
import com.cgiair.airqualityinfo.Service.v2.AirQualityInfoV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController
@RequestMapping("airQualityInfo/api/v2")
public class AirQualityControllerv2 {


    private AirQualityInfoV2 airQualityInfoV2;



    @Autowired
    public AirQualityControllerv2(AirQualityInfoV2 airQualityInfoV2) {
        this.airQualityInfoV2 = airQualityInfoV2;
    }

    @GetMapping("/city")
    public ResponseEntity<DataListV2> getAirQualityData(@RequestParam(value = "country") String country,
                                                        @RequestParam(value = "city") String city) throws IOException {
        DataListV2 airQuality= airQualityInfoV2.getAirQualityData(country,city);
        return new ResponseEntity<>(airQuality, HttpStatus.ACCEPTED);
    }

}
