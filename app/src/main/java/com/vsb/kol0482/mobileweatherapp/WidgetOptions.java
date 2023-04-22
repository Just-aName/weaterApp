package com.vsb.kol0482.mobileweatherapp;

public enum WidgetOptions {
    NONE("---"),
    TEPLOTA("Teplota"),
    VLHKOST("Vlhkost"),
    TLAK("Tlak"),
    RYCHLOSTVETRU("Rychlost větru");

    private String value;

    WidgetOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
