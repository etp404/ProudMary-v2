package com.matthewsimonmould.proudmary_v2;

public class GoogleMapsLinkGenerator {

    private static final String GOOGLE_MAP_API_TEMPLATE = "https://www.google.co.uk/maps/@%s,%s,10z";

    public static String generateLinkForLongLat(double latitude, double longitude) {
        return String.format(GOOGLE_MAP_API_TEMPLATE, latitude, longitude);
    }
}
