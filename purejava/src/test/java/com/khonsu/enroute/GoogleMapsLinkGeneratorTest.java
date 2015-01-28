package com.khonsu.enroute;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class GoogleMapsLinkGeneratorTest {
    @Test
    public void testThatMapLinkIsGeneratorAsExpected() {

        String latitude =  "16.4";
		String longitude =  "-2.2";

        assertEquals("https://www.google.co.uk/maps/@16.4,-2.2,10z", GoogleMapsLinkGenerator.generateLinkForLatLong(latitude, longitude));
    }

	@Test
	public void testThatGeocodingLinkIsGeneratedCorrectly() throws MalformedURLException {
		String place = "Birmingham";
		assertEquals(new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+place), GoogleMapsLinkGenerator.generateLinkForGeoCoding(place));
	}

	@Test
	public void testThatDirectionsLinkIsGeneratedCorrectly() throws MalformedURLException {
		String currentLatitude = "5";
		String currentLongitude = "10";
		String destination="Leeds";

		URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=%s", currentLatitude, currentLongitude, destination));
		assertEquals(url, GoogleMapsLinkGenerator.generateLinkForDirections(currentLatitude, currentLongitude, destination));
	}

	@Test
	public void testThatDirectionsLinkIsGeneratedCorrectlyWhenDestinationIsPostcode() throws MalformedURLException {
		String currentLatitude = "5";
		String currentLongitude = "10";

		URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=AB6%%207RQ", currentLatitude, currentLongitude));
		assertEquals(url, GoogleMapsLinkGenerator.generateLinkForDirections(currentLatitude, currentLongitude, "AB6 7RQ"));
	}

}