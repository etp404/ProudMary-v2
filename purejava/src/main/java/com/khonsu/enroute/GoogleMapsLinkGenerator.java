package com.khonsu.enroute;

import org.apache.commons.httpclient.URIUtil;

import java.net.MalformedURLException;
import java.net.URL;


public class GoogleMapsLinkGenerator {

    private static final String GOOGLE_MAP_API_TEMPLATE = "http://maps.google.co.uk/maps?q=%s,%s";
	private static final String GOOGLE_MAPS_API_BASE = "https://maps.googleapis.com/maps/api";
    private static final String GOOGLE_DIRECTION_API_TEMPLATE = GOOGLE_MAPS_API_BASE  + "/directions/json?origin=%s,%s&destination=%s";
	private static final String GOOGLE_GEOCODING = GOOGLE_MAPS_API_BASE + "/geocode/json?address=%s";

    public static String generateLinkForLatLong(String latitude, String longitude) {
        return String.format(GOOGLE_MAP_API_TEMPLATE, latitude, longitude);
    }

	public static URL generateLinkForDirections(String currentLat, String currentLong, String destination) throws MalformedURLException {
		String uriEncodedString = String.format(GOOGLE_DIRECTION_API_TEMPLATE, currentLat, currentLong, URIUtil.encode(destination));
		return new URL(uriEncodedString);
	}

	public static URL generateLinkForGeoCoding(String location) throws MalformedURLException {
		return new URL (String.format(GOOGLE_GEOCODING, location));
	}
}
