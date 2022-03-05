package com.cgiair.apiGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/airQualityInfoServiceFallBack")
    public String airQualityInfoServiceFallBack(){
        return ("Air Quality Info Service is taking longer then expected" +
                " Please make a request later. Thanks");
    }

    @GetMapping("/favoriteServiceFallBack")
    public String favoriteServiceFallBack(){
        return ("Favorites Service is taking longer then expected" +
                " Please make a request later. Thanks");
    }

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBack(){
        return ("User Service is taking longer then expected" +
                " Please make a request later. Thanks");
    }





}
