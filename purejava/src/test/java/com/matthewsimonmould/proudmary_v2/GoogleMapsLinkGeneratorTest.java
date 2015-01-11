package com.matthewsimonmould.proudmary_v2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoogleMapsLinkGeneratorTest {
    @Test
    public void testThatLinkIsGeneratorAsExpected() {

        double latitude =  16.4;
        double longitude =  -2.2;

        assertEquals("https://www.google.co.uk/maps/@16.4,-2.2", GoogleMapsLinkGenerator.generateLinkForLongLat(latitude, longitude));
    }

}
