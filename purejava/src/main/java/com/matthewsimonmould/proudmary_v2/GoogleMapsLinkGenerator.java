package com.matthewsimonmould.proudmary_v2;

import java.net.MalformedURLException;
import java.net.URL;


public class GoogleMapsLinkGenerator {

    private static final String GOOGLE_MAP_API_TEMPLATE = "https://www.google.co.uk/maps/@%s,%s,10z";
    private static final String GOOGLE_DIRECTION_API_TEMPLATE = "https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=%s";

    public static String generateLinkForLongLat(double latitude, double longitude) {
        return String.format(GOOGLE_MAP_API_TEMPLATE, latitude, longitude);
    }

	public static URL generateLinkForDirections(String currentLat, String currentLong, String destination) {
		try {
			return new URL(String.format(GOOGLE_DIRECTION_API_TEMPLATE, currentLat, currentLong, destination));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null; //TODO: Cover this error.
		}
	}
}
