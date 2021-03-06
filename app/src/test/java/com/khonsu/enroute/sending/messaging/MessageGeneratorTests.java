package com.khonsu.enroute.sending.messaging;


import com.khonsu.enroute.Estimate;
import com.khonsu.enroute.settings.UpdaterSettings;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class MessageGeneratorTests {

	private String duration = "2.5 hours";
	private String originLat = "16";
	private String originLong = "2";
	private UpdaterSettings mockUpdaterSettings = mock(UpdaterSettings.class);


	@Test
	public void testThatMessageIsAsExpectedWhenBothTimeEstimateAndMapLinkAreRequired() {
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(true);
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings);

		String expectedMessage = String.format("I should arrive in approximately %s. My current location is: http://maps.google.co.uk/maps?q=%s,%s. Update sent by En Route!", duration, originLat, originLong);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong, new Estimate(duration, null));
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testThatMessageIsAsExpectedWhenBothTimeEstimateButNotMapLinkIsRequired() {
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(false);
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings);

		String expectedMessage = String.format("I should arrive in approximately %s. Update sent by En Route!", duration);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong, new Estimate(duration, null));
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testThatMessageIsAsExpectedWhenDistanceIsRequired() {
		String distanceInMiles = "5.2 miles";
		String distanceInMeters = "8400";
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(true);
		when(mockUpdaterSettings.isIncludeDistanceInMessage()).thenReturn(true);
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings);

		String expectedMessage = String.format("I should arrive in approximately %s. I am %s away. My current location is: http://maps.google.co.uk/maps?q=%s,%s. Update sent by En Route!", duration, distanceInMiles, originLat, originLong);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong, new Estimate(duration, distanceInMeters));
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testThatMessageIsAsExpectedWhenDestinationIsRequired() {
		String destination = "Aberystwyth";
		when(mockUpdaterSettings.isIncludeDestinationInMessage()).thenReturn(true);
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(true);
		when(mockUpdaterSettings.getDestination()).thenReturn(destination);
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings);

		String expectedMessage = String.format("I am en route to %s. I should arrive in approximately %s. My current location is: http://maps.google.co.uk/maps?q=%s,%s. Update sent by En Route!", destination, duration, originLat, originLong);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong, new Estimate(duration, null));
		assertEquals(expectedMessage, actualMessage);
	}
}
