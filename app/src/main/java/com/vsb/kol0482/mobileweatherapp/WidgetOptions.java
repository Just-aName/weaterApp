package com.vsb.kol0482.mobileweatherapp;

public enum WidgetOptions {
    NONE("Skrýt widget"),
    CLOUDBASE("Výška mraků"),
    OUTHUMIDITY("Venkovní vlhkost"),
    PRESURE("Armosférický tlak abs."),
    BAROMETER("Armosféruický tlak p.n.h.m."),
    DEWPOINT("Rosný bod"),
    RAINTOTAL("Celkové srážky"),
    HEATINDEX("Teplotní index"),
    INDEWPOINT("Rosný bod vevnitř"),
    DAYRAIN("Srážky za den"),
    ALTIMETER("Výškový tlak"),
    WINDCHILL("Ochlazení větrem"),
    APPTEMP("Pocitová teplota"),
    OUTTEMP("Venkovní teplota"),
    MAXSOLARRAD("Sluneční svit"),
    HUMIDITYINDEX("Index vlhkosti"),
    HOURRAIN("Srážky za hodinu"),
    WINDGUST("Náraz větru"),
    INTEMP("Vnitřní teplota"),
    RAIN24("Srážky za 24 hodin"),
    WINDDIRECTION("Směr větru"),
    WINDSPEED("Rychlost větru"),
    INHUMIDITY("Vnitřní vlhkost");

    private String value;

    WidgetOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WidgetOptions fromValue(String value) {
        for (WidgetOptions option : WidgetOptions.values()) {
            if (option.getValue().equalsIgnoreCase(value)) {
                return option;
            }
        }
        return null;
    }
}

