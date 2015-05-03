package com.khonsu.enroute.sending.messaging;


import com.khonsu.enroute.Estimate;
import com.khonsu.enroute.GoogleMapsDurationGetter;
import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.UnableToGetEstimatedJourneyTimeException;
import com.khonsu.enroute.settings.UpdaterSettings;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class MessageGeneratorTests {

	private String duration = "2.5 hours";
	private String distance = "50 miles";
	private String originLat = "16";
	private String originLong = "2";
	private UpdaterSettings mockUpdaterSettings = mock(UpdaterSettings.class);
	private GoogleMapsDurationGetter mockGoogleMapsDurationGetter = mock(GoogleMapsDurationGetter.class);


	@Test
	public void testThatMessageIsAsExpectedWhenBothTimeEstimateAndMapLinkAreRequired() throws UnableToGetEstimatedJourneyTimeException {
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(true);
		when(mockGoogleMapsDurationGetter.getJourneyEstimate(anyString(), anyString(), anyString(), any(ModeOfTransport.class))).thenReturn(new Estimate(duration, distance));
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings, mockGoogleMapsDurationGetter);

		String expectedMessage = String.format("I should arrive in approximately %s. My current location is: http://maps.google.co.uk/maps?q=%s,%s. Update sent by En Route!", duration, originLat, originLong);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong);
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testThatMessageIsAsExpectedWhenBothTimeEstimateButNotMapLinkIsRequired() throws UnableToGetEstimatedJourneyTimeException {
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(false);
		when(mockGoogleMapsDurationGetter.getJourneyEstimate(anyString(), anyString(), anyString(), any(ModeOfTransport.class))).thenReturn(new Estimate(duration, distance));
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings, mockGoogleMapsDurationGetter);

		String expectedMessage = String.format("I should arrive in approximately %s. Update sent by En Route!", duration);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong);
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testThatMessageIsAsExpectedWhenDistanceIsRequired() throws UnableToGetEstimatedJourneyTimeException {
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(true);
		when(mockUpdaterSettings.isIncludeDistanceInMessage()).thenReturn(true);
		when(mockGoogleMapsDurationGetter.getJourneyEstimate(anyString(), anyString(), anyString(), any(ModeOfTransport.class))).thenReturn(new Estimate(duration, distance));
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings, mockGoogleMapsDurationGetter);

		String expectedMessage = String.format("I should arrive in approximately %s. I am %s away. My current location is: http://maps.google.co.uk/maps?q=%s,%s. Update sent by En Route!", duration, distance, originLat, originLong);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong);
		assertEquals(expectedMessage, actualMessage);
	}
}
