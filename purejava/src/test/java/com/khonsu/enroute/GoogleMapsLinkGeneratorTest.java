package com.khonsu.enroute;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class GoogleMapsLinkGeneratorTest {
    String mode = "walking";

    @Test
    public void testThatMapLinkIsGeneratorAsExpected() {

        String latitude =  "43.4727005";
		String longitude =  "-2.2986971";

        assertEquals("http://maps.google.co.uk/maps?q=43.4727005,-2.2986971", GoogleMapsLinkGenerator.generateLinkForLatLong(latitude, longitude));
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

		URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=%s&mode=%s&units=imperial", currentLatitude, currentLongitude, destination, mode));
		assertEquals(url, GoogleMapsLinkGenerator.generateLinkForDirections(currentLatitude, currentLongitude, destination, mode));
	}

	@Test
	public void testThatDirectionsLinkIsGeneratedCorrectlyWhenDestinationIsPostcode() throws MalformedURLException {
		String currentLatitude = "5";
		String currentLongitude = "10";

		URL url = new URL(String.format("https://maps.googleapis.com/maps/api/directions/json?origin=%s,%s&destination=AB6%%207RQ&mode=%s&units=imperial", currentLatitude, currentLongitude, mode));
		assertEquals(url, GoogleMapsLinkGenerator.generateLinkForDirections(currentLatitude, currentLongitude, "AB6 7RQ", mode));
	}

	@Test
	public void testThatAutocompleteLinkIsGeneratedCorrectlyForAddressWithNoSpaces() throws MalformedURLException {
		String place = "Bradford";
		URL url = new URL(String.format("http://54.171.194.38/google_places_proxy.php?location_input=%s", place));

		assertEquals(url, GoogleMapsLinkGenerator.generateLinkForAutocomplete(place));
	}

	@Test
	public void testThatAutocompleteLinkIsGeneratedCorrectlyForAddressWithSpaces() throws MalformedURLException {
		URL url = new URL("http://54.171.194.38/google_places_proxy.php?location_input=Adlington%%20Road");

		assertEquals(url, GoogleMapsLinkGenerator.generateLinkForAutocomplete("Adlington Road"));
	}

}
