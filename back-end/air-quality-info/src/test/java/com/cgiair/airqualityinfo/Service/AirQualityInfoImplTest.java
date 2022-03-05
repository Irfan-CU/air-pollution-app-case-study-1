package com.cgiair.airqualityinfo.Service;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQuality;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class AirQualityInfoImplTest {

    @Mock
    //private RestTemplate restTemplate;
    private ObjectMapper objectMapper;


    RestTemplate template = new RestTemplate();
    private String parentUrl = "http://api.airvisual.com/v2/";
    private static String apiKey = "3e2ec32e-941c-4e2e-a870-3781ea3df64d";



    @InjectMocks
    private AirQualityInfoImpl airQualityInfoImpl;
    private AirQuality airQuality;
    private Optional optional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        airQuality = new AirQuality();
        optional = Optional.of(this.airQuality);
    }

    @AfterEach
    public void tearDown() {
        airQuality = null;
    }



    @Test
    void givenCityThenShouldReturnPollutionData() {

        String url = parentUrl+"city?city=Montreal&state=Quebec&country=Canada&key="+apiKey;
        airQuality = template.getForObject(url,AirQuality.class);
        Assertions.assertEquals("success", airQuality.getStatus());
        Assertions.assertEquals("Montreal",airQuality.getData().getCity(),"This test should return Montreal");
        Assertions.assertNotNull(airQuality.getData().getCurrent().getPollution());
    }

    @Test
    void givenUserIdThenShouldReturnFavoritesCities() {

        //Ask for Durai Input on testing this method

    }

    @Test
    void shouldReturnListOfAllSupportedCountries() throws JsonProcessingException {
        String URL = parentUrl+"countries?key="+apiKey;
        ResponseEntity<String> result = template.getForEntity(URL, String.class);
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assert.assertFalse(result.getBody().isEmpty());
        Assertions.assertTrue(result.getBody().contains("country"));
    }

    @Test
    void shouldReturnListOfAllSupportedStates() {

        String URL = parentUrl+"states?country=Canada&key="+apiKey;
        ResponseEntity<String> result = template.getForEntity(URL, String.class);
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assert.assertFalse(result.getBody().isEmpty());
        Assertions.assertTrue(result.getBody().contains("state"));
    }

    @Test
    void shouldReturnListOfAllSupportedCities() {
        String URL = parentUrl+"cities?state=Quebec&country=Canada&key="+apiKey;
        ResponseEntity<String> result = template.getForEntity(URL, String.class);
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assert.assertFalse(result.getBody().isEmpty());
        Assertions.assertTrue(result.getBody().contains("city"));
    }
}