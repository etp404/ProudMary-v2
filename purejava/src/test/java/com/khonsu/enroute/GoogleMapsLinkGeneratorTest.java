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

        assertEquals("https://www.google.co.uk/maps/@16.4,-2.2,10z", GoogleMapsLinkGenerator.generateLinkForLongLat(latitude, longitude));
    }

	@Test
	public void testThatGeocodingLinkIsGeneratedCorrectly() throws MalformedURLException {
		String place = "Birmingham";
		assertEquals(new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+place), GoogleMapsLinkGenerator.generateLinkForGeoCoding(place));
	}

}
