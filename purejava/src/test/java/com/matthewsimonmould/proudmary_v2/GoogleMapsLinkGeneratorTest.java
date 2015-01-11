package com.matthewsimonmould.proudmary_v2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoogleMapsLinkGeneratorTest {
    @Test
    public void testThatLinkIsGeneratorAsExpected() {

        String longitude =  "53.4290463";
        String latitude =  "-2.2407765";

        assertEquals("https://www.google.co.uk/maps/@53.4290463,-2.2407765", GoogleMapsLinkGenerator.generateLinkForLongLat(longitude, latitude));
    }

}
