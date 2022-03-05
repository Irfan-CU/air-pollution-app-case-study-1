package com.cgiair.airqualityinfo.Controller;

import com.cgiair.airqualityinfo.Model.AirQuality.*;
import com.cgiair.airqualityinfo.Service.AirQualityInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AirQualityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AirQualityInfo airQualityInfo;

    @InjectMocks
    private AirQualityController airQualityController;
    private static ObjectMapper objectMapper;
    private AirQuality airQuality;
    private Data data;
    private Current current;
    private Location location;
    private Pollution pollution;
    //private List<Blog> blogList;
    private List<String> countriesList = new ArrayList<>();
    private List<String> statesList = new ArrayList<>();
    private List<String> citiesList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(airQualityController).build();
        //setControllerAdvice(new GlobalExceptionHandler()).build();
        pollution = new Pollution("2021-08-23T18:00:00.000Z","66","p2","27","p2");
        current = new Current(pollution);
        data = new Data("Toronto","Ontario","Canada",current);
        airQuality = new AirQuality("success",data);
        countriesList.add("Canada");
        countriesList.add("USA");
        countriesList.add("Australia");
        statesList.add("Alberta");
        statesList.add("Ontario");
        statesList.add("Quebec");
        citiesList.add("Toronto");
        citiesList.add("Montreal");
        citiesList.add("Vancouver");



    }

    @AfterEach
    void tearDown() {
        airQuality = null;
        data = null;
        current = null;
        pollution = null;
    }

    @Test
    void shouldReturnPollutionDataForACity() throws Exception {
        when(airQualityInfo.getAirQualityData(any(),any(),any())).thenReturn(this.airQuality);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/airQualityInfo/api/v1/city?country=Canada&state=Ontario&city=Toronto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(this.airQuality)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testGetAirQualityData() {
        // write tests here ask Durai

    }

    @Test
    void shouldReturnListOfAllSupportedCountries() throws Exception {


        when(airQualityInfo.getAllSupportedCountries()).thenReturn(this.countriesList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/airQualityInfo/api/v1/supportedCountries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(this.countriesList)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[\"Canada\",\"USA\",\"Australia\"]"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAllSupportedStates() throws Exception {
        when(airQualityInfo.getAllSupportedStates(any())).thenReturn(this.statesList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/airQualityInfo/api/v1/supportedStates?country=Canada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(this.statesList)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[\"Alberta\",\"Ontario\",\"Quebec\"]"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getAllSupportedCities() throws Exception {
        when(airQualityInfo.getAllSupportedCities(any(),any())).thenReturn(this.citiesList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/airQualityInfo/api/v1/supportedCities?country=Canada&state=Quebec")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(this.citiesList)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[\"Toronto\",\"Montreal\",\"Vancouver\"]"))
                .andDo(MockMvcResultHandlers.print());

    }

    public static String asJsonString(final Object obj) {
        System.out.println(obj);
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}