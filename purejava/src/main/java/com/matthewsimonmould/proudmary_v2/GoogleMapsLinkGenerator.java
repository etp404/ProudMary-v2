package com.matthewsimonmould.proudmary_v2;

public class GoogleMapsLinkGenerator {

    private static String googleMapAPITemplate = "https://www.google.co.uk/maps/@%s,%s";

    public static String generateLinkForLongLat(String longitude, String latitude) {
        return String.format(googleMapAPITemplate, longitude, latitude);
    }
}
