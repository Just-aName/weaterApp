package com.vsb.kol0482.mobileweatherapp;

public enum WidgetOptionsUnits {
    NONE(""),
    CLOUDBASE("m"),
    OUTHUMIDITY("%"),
    PRESURE("mbar"),
    BAROMETER("mbar"),
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
    WINDGUST("m/s"),
    INTEMP("°C"),
    RAIN24("mm"),
    WINDDIRECTION("°"),
    WINDSPEED("m/s"),
    INHUMIDITY("%");

    private String value;

    WidgetOptionsUnits(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
