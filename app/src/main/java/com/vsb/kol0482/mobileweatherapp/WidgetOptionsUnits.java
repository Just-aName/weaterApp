package com.vsb.kol0482.mobileweatherapp;

public enum WidgetOptionsUnits {
    NONE(""),
    CLOUDBASE("m"),
    OUTHUMIDITY("%"),
    PRESURE("mbar"),
    BAROMETER("mbar"),
    RAINPEROUR("mm/h"),
    DEWPOINT("°C"),
    RAINTOTAL("mm"),
    HEATINDEX("°C"),
    INDEWPOINT("°C"),
    DAYRAIN("mm"),
    ALTIMETER("mbar"),
    WINDCHILL("°C"),
    APPTEMP("°C"),
    OUTTEMP("°C"),
    MAXSOLARRAD("W/m^2"),
    HUMIDITYINDEX("°C"),
    HOURRAIN("mm"),
    WINDGUST("mps"),
    INTEMP("°C"),
    RAIN24("mm"),
    WINDDIRECTION("°"),
    WINDSPEED("mps"),
    INHUMIDITY("%");

    private String value;

    WidgetOptionsUnits(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
