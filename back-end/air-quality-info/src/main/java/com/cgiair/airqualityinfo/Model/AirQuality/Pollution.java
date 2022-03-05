package com.cgiair.airqualityinfo.Model.AirQuality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Pollution {

    private String ts;
    private String aqius;
    private String mainus;
    private String aqicn;
    private String maincn;

    public Pollution(String ts, String aqius, String mainus, String aqicn, String maincn) {
        this.ts = ts;
        this.aqius = aqius;
        this.mainus = mainus;
        this.aqicn = aqicn;
        this.maincn = maincn;
    }

    public Pollution() {
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getAqius() {
        return aqius;
    }

    public void setAqius(String aqius) {
        this.aqius = aqius;
    }

    public String getMainus() {
        return mainus;
    }

    public void setMainus(String mainus) {
        this.mainus = mainus;
    }

    public String getAqicn() {
        return aqicn;
    }

    public void setAqicn(String aqicn) {
        this.aqicn = aqicn;
    }

    public String getMaincn() {
        return maincn;
    }

    public void setMaincn(String maincn) {
        this.maincn = maincn;
    }
}
