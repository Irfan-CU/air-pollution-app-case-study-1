package com.cgiair.airqualityinfo.Service;

import com.cgiair.airqualityinfo.Model.AirQuality.AirQuality;
import com.cgiair.airqualityinfo.Model.AirQuality.AirQualityList;
import com.cgiair.airqualityinfo.Model.Country.Country;
import com.cgiair.airqualityinfo.Model.Country.CountryList;
import com.cgiair.airqualityinfo.Model.Country.Data;
import com.cgiair.airqualityinfo.Model.Favorite.FavoriteItem;
import com.cgiair.airqualityinfo.Model.v2.AirQuality.AirQualityV2;
import com.cgiair.airqualityinfo.Model.v2.AirQuality.DataListV2;
import com.cgiair.airqualityinfo.Model.v2.AirQuality.DataV2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.checkerframework.checker.units.qual.A;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "airQualityData")
@Service
public class AirQualityInfoImpl implements AirQualityInfo {


    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private static String parent_url = "http://api.airvisual.com/v2/";
    private static String parent_urlV2 = "http://api.weatherbit.io/v2.0/current";

//    private static String API_Key = "3e2ec32e-941c-4e2e-a870-3781ea3df64d";
    private static String API_Key = "3e2ec32e-941c-4e2e-a870-3781ea3df64d";

    private AirQualityInfoFuncs airQualityInfoFuncs;

    @Autowired
    public AirQualityInfoImpl(RestTemplate restTemplate, ObjectMapper objectMapper, AirQualityInfoFuncs airQualityInfoFuncs) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.airQualityInfoFuncs = airQualityInfoFuncs;
    }


    @Override
    @Cacheable(value = "getAirQualityData", key ="#City")
    public AirQuality getAirQualityData(String Country,String State,String City) throws JsonProcessingException {

        String url = parent_url+"city?city="+
                City+"&state="+
                State+"&country="+
                Country+" &key="+API_Key;

        AirQuality res = new RestTemplate().getForObject(url,AirQuality.class);
        return res;
    }

    @Override
    public AirQualityList getAirQualityDataForFavCities(String token) {
        System.out.println("inside getAirQualityDataForFavCities | token: " + token);
        AirQualityList tempFavoritesList = new AirQualityList();
        String url = "http://cgiair-favorite/fav/";
        //String url = "http://localhost:8020/fav";
        System.out.println("url: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<List<FavoriteItem>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<FavoriteItem>>() {});
        System.out.println(response.getBody());

        response.getBody().stream().forEach((item)-> {
            try {
                tempFavoritesList.add(getAirQualityData(item.getCountry(),item.getState(),item.getCity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return tempFavoritesList;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    @Override
    @Cacheable(value = "countriesList")
    public List<String> getAllSupportedCountries() throws IOException {

//        String url = parent_url+"countries?key="+API_Key;
//        InputStream is = null;
//        try {
//            is = new URL(url).openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//            String jsonText = readAll(rd);
//            JSONObject json = new JSONObject(jsonText);
//            System.out.println(json.getJSONArray("data").getJSONObject(2).get("country"));
//            //return json;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } finally {
//            is.close();
//        }
        System.out.println("I am in countries List");
        List<String> tempList = new ArrayList<>();
        String URL = parent_url+"countries?key="+API_Key;
        URL url = new URL(URL);
        JsonObject rootobj = airQualityInfoFuncs.jsonObjectImplementation(URL);
        JsonArray temp = new JsonArray();
        temp = rootobj.get("data").getAsJsonArray();
        for (int i=0;i<temp.size();i++){
            //String str = "";
            String res = temp.get(i).getAsJsonObject().get("country").toString();
            String resp = res.substring(1,res.length()-1);
            tempList.add(resp);
        }




//        String res = new RestTemplate().getForObject(url,String.class);
//        Map<?,?> tempCountries = objectMapper.readValue(res,Map.class);
//        ArrayList<?> tempData = new ArrayList<>();
//
//        tempCountries.entrySet().stream().map(entry -> "key is: "+entry.getKey() + " & Value is: " + Arrays.stream(entry.getValue())
//                .collect(Collectors.joining(","))).forEach(System.out::println);
//
//        tempCountries.entrySet().stream().map()
//        for (Map.Entry<?, ?> entry : tempCountries.entrySet()) {
//            if(entry.getKey()=="data"){
//                tempData = (ArrayList<?>) entry.getValue();
//            }
//
//        }
//        System.out.println(tempData.stream()..collect(Collectors.joining("="));
        return tempList;
    }

    @Override
    @Cacheable(value = "statesList")
    public List<String> getAllSupportedStates(String Country) throws IOException {

        List<String> tempStates = new ArrayList<>();
        String URL = parent_url+"states?country="+Country+"&key="+API_Key;
        JsonObject rootobj = airQualityInfoFuncs.jsonObjectImplementation(URL);
        JsonArray temp = new JsonArray();
        temp = rootobj.get("data").getAsJsonArray();
        for (int i=0;i<temp.size();i++){
            String res = temp.get(i).getAsJsonObject().get("state").toString();
            String resp = res.substring(1,res.length()-1);
            tempStates.add(resp);
        }
        return tempStates;
    }

    @Override
    @Cacheable(value = "citiesList")
    public List<String> getAllSupportedCities(String Country, String State) throws IOException {

        List<String> tempCities = new ArrayList<>();
        if(State.contains(" ")){
            State = State.replace(" ","%20");
        }
        String URL = parent_url+"cities?state="+State+"&country="+Country+"&key="+API_Key;
        JsonObject rootobj = airQualityInfoFuncs.jsonObjectImplementation(URL);
        JsonArray temp = new JsonArray();
        temp = rootobj.get("data").getAsJsonArray();
        for (int i=0;i<temp.size();i++){
            String res = temp.get(i).getAsJsonObject().get("city").toString();
            String resp = res.substring(1,res.length()-1);
            tempCities.add(resp);
        }

        return tempCities;
    }

    @Override

    public List<String> getNearestCityDataByIP(){

        String url = parent_url+"nearest_city?key="+API_Key;
        List<String>tempCountries = restTemplate.getForObject(url,List.class);
        return tempCountries;
    }

    @Override
    public List<String> getNearestCityDataByGPS(String Latitude, String Longitude){

        String url = parent_url+"nearest_city?lat="+Latitude+"&lon="+Longitude+"&key="+API_Key;
        List<String>tempCountries = restTemplate.getForObject(url,List.class);
        return tempCountries;
    }
}
