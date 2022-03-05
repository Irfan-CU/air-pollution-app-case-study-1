package com.cgiair.airqualityinfo.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
public class AirQualityInfoFuncs {

    public JsonObject jsonObjectImplementation(String parentUrl) throws IOException {
        java.net.URL url = new URL(parentUrl);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new com.google.gson.JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        return root.getAsJsonObject(); //May be an array, may be an object.
    }
}
