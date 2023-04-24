package com.vsb.kol0482.mobileweatherapp;

import org.json.JSONObject;

public class WeatherRVModal {

    private String valueName;
    private String valueNumber;
    private String unit;


    public WeatherRVModal(String valueNumber, String valueName, String unit) {
        this.valueName = valueName;
        this.valueNumber = valueNumber;
        this.unit = unit;
    }

    public String getvalueName() {
        return valueName;
    }

    public void setValueName(String value) {
        this.valueName = value;
    }

    public String getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(String value) {
        this.valueNumber = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String value) {
        this.unit = value;
    }


}
