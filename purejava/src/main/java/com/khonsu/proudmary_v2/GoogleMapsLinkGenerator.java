package com.khonsu.proudmary_v2;

import java.net.MalformedURLException;
import java.net.URL;


public class GoogleMapsLinkGenerator {

    private static final String GOOGLE_MAP_API_TEMPLATE = "https://www.google.co.uk/maps/@%s,%s,10z";
	private static final String GOOGLE_MAPS_API_BASE = "https://maps.googleapis.com/maps/api";
    private static final String GOOGLE_DIRECTION_API_TEMPLATE = GOOGLE_MAPS_API_BASE  + "/directions/json?origin=%s,%s&destination=%s";
	private static final String GOOGLE_GEOCODING = GOOGLE_MAPS_API_BASE + "/geocode/json?address=%s";

    public static String generateLinkForLongLat(String latitude, String longitude) {
        return String.format(GOOGLE_MAP_API_TEMPLATE, latitude, longitude);
    }

	public static URL generateLinkForDirections(String currentLat, String currentLong, String destination) throws MalformedURLException {
		return new URL(String.format(GOOGLE_DIRECTION_API_TEMPLATE, currentLat, currentLong, destination));
	}

	public static URL generateLinkForGeoCoding(String location) throws MalformedURLException {
		return new URL (String.format(GOOGLE_GEOCODING, location));
	}
}
