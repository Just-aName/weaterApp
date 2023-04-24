package com.vsb.kol0482.mobileweatherapp;

public class ApiDataBinder {
    public static String GetApiVersion(String variable) {
        switch (variable) {
            case "Výška mraků":
                return "cloudbase_meter";
            case "Venkovní vlhkost":
                return "outHumidity";
            case "Armosférický tlak abs.":
                return "pressure_mbar";
            case "Armosféruický tlak p.n.h.m.":
                return "barometer_mbar";
            case "Rosný bod":
                return "dewpoint_C";
            case "Celkové srážky":
                return "rainTotal";
            case "Teplotní index":
                return "heatindex_C";
            case "Rosný bod vevnitř":
                return "inDewpoint_C";
            case "Srážky za den":
                return "dayRain_mm";
            case "Výškový tlak":
                return "altimeter_mbar";
            case "Ochlazení větrem":
                return "windchill_C";
            case "Pocitová teplota":
                return "appTemp_C";
            case "Venkovní teplota":
                return "outTemp_C";
            case "Sluneční svit":
                return "maxSolarRad_Wpm2";
            case "Index vlhkosti":
                return "humidex_C";
            case "Srážky za hodinu":
                return "hourRain_mm";
            case "Náraz větru":
                return "windGust_mps";
            case "Vnitřní teplota":
                return "inTemp_C";
            case "Rážky za 24 hodin":
                return "rain24_mm";
            case "Směr větru":
                return "windDir";
            case "Rychlost větru":
                return "windSpeed_mps";
            case "Vnitřní vlhkost":
                return "inHumidity";
            case "time":
                return "time";
            default:
                return "";
        }
    }

    public static String GetAppStringVersion(String variable) {
        switch (variable) {
            case "cloudbase_meter":
                return WidgetOptions.CLOUDBASE.getValue();
            case "outHumidity":
                return WidgetOptions.OUTHUMIDITY.getValue();
            case "pressure_mbar":
                return WidgetOptions.PRESURE.getValue();
            case "barometer_mbar":
                return WidgetOptions.BAROMETER.getValue();
            case "dewpoint_C":
                return WidgetOptions.DEWPOINT.getValue();
            case "rainTotal":
                return WidgetOptions.RAINTOTAL.getValue();
            case "heatindex_C":
                return WidgetOptions.HEATINDEX.getValue();
            case "inDewpoint_C":
                return WidgetOptions.INDEWPOINT.getValue();
            case "dayRain_mm":
                return WidgetOptions.DAYRAIN.getValue();
            case "altimeter_mbar":
                return WidgetOptions.ALTIMETER.getValue();
            case "windchill_C":
                return WidgetOptions.WINDCHILL.getValue();
            case "appTemp_C":
                return WidgetOptions.APPTEMP.getValue();
            case "outTemp_C":
                return WidgetOptions.OUTTEMP.getValue();
            case "maxSolarRad_Wpm2":
                return WidgetOptions.MAXSOLARRAD.getValue();
            case "humidex_C":
                return WidgetOptions.HUMIDITYINDEX.getValue();
            case "hourRain_mm":
                return WidgetOptions.HOURRAIN.getValue();
            case "windGust_mps":
                return WidgetOptions.WINDGUST.getValue();
            case "inTemp_C":
                return WidgetOptions.INTEMP.getValue();
            case "rain24_mm":
                return WidgetOptions.RAIN24.getValue();
            case "windDir":
                return WidgetOptions.WINDDIRECTION.getValue();
            case "windSpeed_mps":
                return WidgetOptions.WINDSPEED.getValue();
            case "inHumidity":
                return WidgetOptions.INHUMIDITY.getValue();
            case "time":
                return "time";
            default:
                return "";
        }
    }

    public static WidgetOptions GetAppEnumVersion(String variable) {
        switch (variable) {
            case "cloudbase_meter":
                return WidgetOptions.CLOUDBASE;
            case "outHumidity":
                return WidgetOptions.OUTHUMIDITY;
            case "pressure_mbar":
                return WidgetOptions.PRESURE;
            case "barometer_mbar":
                return WidgetOptions.BAROMETER;
            case "dewpoint_C":
                return WidgetOptions.DEWPOINT;
            case "rainTotal":
                return WidgetOptions.RAINTOTAL;
            case "heatindex_C":
                return WidgetOptions.HEATINDEX;
            case "inDewpoint_C":
                return WidgetOptions.INDEWPOINT;
            case "dayRain_mm":
                return WidgetOptions.DAYRAIN;
            case "altimeter_mbar":
                return WidgetOptions.ALTIMETER;
            case "windchill_C":
                return WidgetOptions.WINDCHILL;
            case "appTemp_C":
                return WidgetOptions.APPTEMP;
            case "outTemp_C":
                return WidgetOptions.OUTTEMP;
            case "maxSolarRad_Wpm2":
                return WidgetOptions.MAXSOLARRAD;
            case "humidex_C":
                return WidgetOptions.HUMIDITYINDEX;
            case "hourRain_mm":
                return WidgetOptions.HOURRAIN;
            case "windGust_mps":
                return WidgetOptions.WINDGUST;
            case "inTemp_C":
                return WidgetOptions.INTEMP;
            case "rain24_mm":
                return WidgetOptions.RAIN24;
            case "windDir":
                return WidgetOptions.WINDDIRECTION;
            case "windSpeed_mps":
                return WidgetOptions.WINDSPEED;
            case "inHumidity":
                return WidgetOptions.INHUMIDITY;
            default:
                return WidgetOptions.NONE;
        }
    }

    public static String GetApiVersionFromEnum(WidgetOptions variable) {
        switch (variable) {
            case CLOUDBASE:
                return "cloudbase_meter";
            case OUTHUMIDITY:
                return "outHumidity";
            case PRESURE:
                return "pressure_mbar";
            case BAROMETER:
                return "barometer_mbar";
            case DEWPOINT:
                return "dewpoint_C";
            case RAINTOTAL:
                return "rainTotal";
            case HEATINDEX:
                return "heatindex_C";
            case INDEWPOINT:
                return "inDewpoint_C";
            case DAYRAIN:
                return "dayRain_mm";
            case ALTIMETER:
                return "altimeter_mbar";
            case WINDCHILL:
                return "windchill_C";
            case APPTEMP:
                return "appTemp_C";
            case OUTTEMP:
                return "outTemp_C";
            case MAXSOLARRAD:
                return "maxSolarRad_Wpm2";
            case HUMIDITYINDEX:
                return "humidex_C";
            case HOURRAIN:
                return "hourRain_mm";
            case WINDGUST:
                return "windGust_mps";
            case INTEMP:
                return "inTemp_C";
            case RAIN24:
                return "rain24_mm";
            case WINDDIRECTION:
                return "windDir";
            case WINDSPEED:
                return "windSpeed_mps";
            case INHUMIDITY:
                return "inHumidity";
            default:
                return "";
        }
    }

    public static String GetUnitBaseOnEnum(WidgetOptions variable) {
        switch (variable) {
            case CLOUDBASE:
                return "m";
            case OUTHUMIDITY:
                return "%";
            case PRESURE:
                return "mbar";
            case BAROMETER:
                return "mbar";
            case DEWPOINT:
                return "°C";
            case RAINTOTAL:
                return "mm";
            case HEATINDEX:
                return "°C";
            case INDEWPOINT:
                return "°C";
            case DAYRAIN:
                return "mm";
            case ALTIMETER:
                return "mbar";
            case WINDCHILL:
                return "°C";
            case APPTEMP:
                return "°C";
            case OUTTEMP:
                return "°C";
            case MAXSOLARRAD:
                return "W/m^2";
            case HUMIDITYINDEX:
                return "°C";
            case HOURRAIN:
                return "mm";
            case WINDGUST:
                return "mps";
            case INTEMP:
                return "°C";
            case RAIN24:
                return "mm";
            case WINDDIRECTION:
                return "windDir";
            case WINDSPEED:
                return "mps";
            case INHUMIDITY:
                return "%";
            default:
                return "";
        }
    }
}
