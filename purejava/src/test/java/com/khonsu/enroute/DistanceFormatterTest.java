package com.khonsu.enroute;

import junit.framework.TestCase;

public class DistanceFormatterTest extends TestCase {

	public void testThatMetresAreFormattedCorrectlyAsMiles() {
		assertEquals("15.3 miles", DistanceFormatter.formatMetersAsMiles("24601"));
	}
}