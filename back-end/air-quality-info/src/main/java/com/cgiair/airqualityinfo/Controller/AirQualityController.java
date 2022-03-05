package com.cgiair.airqualityinfo.Controller;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQuality;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQualityList;
import com.cgiair.airqualityinfo.Service.AirQualityInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping ("airQualityInfo/api/v1")
//@CrossOrigin (value = "http://localhost:4200", maxAge = 3600*5)
public class AirQualityController {


    private AirQualityInfo airQualityInfo;


    @Autowired
    public AirQualityController(AirQualityInfo airQualityInfo) {
        this.airQualityInfo = airQualityInfo;

    }
    // negative scenarios and exceptions
    @GetMapping("/city")
    public ResponseEntity<AirQuality> getAirQualityData(@RequestParam(value = "country") String country,
                                  @RequestParam(value = "state") String state,
                                  @RequestParam(value = "city") String city) throws IOException {
        AirQuality airQuality= airQualityInfo.getAirQualityData(country,state,city);
        return new ResponseEntity<>(airQuality, HttpStatus.ACCEPTED);
    }

    @GetMapping("/favorites")
    public ResponseEntity<AirQualityList> getFavAirQualityData(@RequestHeader("Authorization") String token) {
        System.out.println("inside favorites api end point");
        AirQualityList airQualityList= airQualityInfo.getAirQualityDataForFavCities(token);
        return new ResponseEntity<>(airQualityList, HttpStatus.ACCEPTED);
    }

    //---------------All these requests should be in the front-end-----------------------//
    @GetMapping("/supportedCountries")
    public ResponseEntity<List<String>> getAllSupportedCountries() throws IOException {
        System.out.println("I am in the controller fro the AIr Quality");
        return new ResponseEntity<>(airQualityInfo.getAllSupportedCountries(),HttpStatus.OK);
    }

    @GetMapping("/supportedStates")
    public ResponseEntity<List<String>> getAllSupportedStates(@RequestParam(value = "country") String country) throws IOException {
        return new ResponseEntity<>(airQualityInfo.getAllSupportedStates(country),HttpStatus.OK);
    }

    @GetMapping("/supportedCities")
    public ResponseEntity<List<String>> getAllSupportedCities(@RequestParam(value = "country") String country,
                                                      @RequestParam(value = "state") String state) throws IOException {
        return new ResponseEntity<>(airQualityInfo.getAllSupportedCities(country,state),HttpStatus.OK);
    }
}
