package com.matthewsimonmould.proudmary_v2;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.ws.rs.core.UriBuilder;

public class GoogleMapsLinkGenerator {

    private static final String GOOGLE_MAP_API_TEMPLATE = "https://www.google.co.uk/maps/@%s,%s,10z";
    private static final String GOOGLE_DIRECTION_API_TEMPLATE = "https://maps.googleapis.com/maps/api/directions/json";

    public static String generateLinkForLongLat(double latitude, double longitude) {
        return String.format(GOOGLE_MAP_API_TEMPLATE, latitude, longitude);
    }

	public static URL generateLinkForDirections(String currentLong, String currentLat, String destination) {
		UriBuilder uriBuilder = UriBuilder.fromUri(GOOGLE_DIRECTION_API_TEMPLATE);
		uriBuilder.queryParam("origin", String.format("%s,%s", currentLat, currentLong));
		uriBuilder.queryParam("destination", destination);
		URI uri = uriBuilder.build();
		try {
			return uri.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace(); //TODO: handle this.
		}
		return null;
	}
}
