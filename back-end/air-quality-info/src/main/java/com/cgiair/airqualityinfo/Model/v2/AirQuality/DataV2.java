package com.cgiair.airqualityinfo.Model.v2.AirQuality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataV2 {

    private DataListV2[] dataArray;

    public DataV2() {
    }

    public DataV2(DataListV2[] dataArray) {
        this.dataArray = dataArray;
    }

    public DataListV2[] getDataArray() {
        return dataArray;
    }

    public void setDataArray(DataListV2[] dataArray) {
        this.dataArray = dataArray;
    }
}
