package com.cgiair.airqualityinfo.Model.AirQuality;

public class AirQuality {

    private String status;
    private Data data;

    public AirQuality(String status, Data data) {
        this.status = status;
        this.data = data;
    }

    public AirQuality() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
