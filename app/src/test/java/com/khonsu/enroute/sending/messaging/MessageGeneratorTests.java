package com.khonsu.enroute.sending.messaging;


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

	private String duration;
	private String originLat;
	private String originLong;
	private UpdaterSettings mockUpdaterSettings;
	private GoogleMapsDurationGetter mockGoogleMapsDurationGetter;

	public MessageGeneratorTests() {
		duration = "2.5 hours";
		originLat = "16";
		originLong = "2";

		mockUpdaterSettings = mock(UpdaterSettings.class);
		mockGoogleMapsDurationGetter = mock(GoogleMapsDurationGetter.class);
	}

	@Test
	public void testThatMessageIsAsExpectedWhenBothTimeEstimateAndMapLinkAreRequired() throws UnableToGetEstimatedJourneyTimeException {
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(true);
		when(mockGoogleMapsDurationGetter.getEstimatedJourneyTime(anyString(), anyString(), anyString(), any(ModeOfTransport.class))).thenReturn(duration);
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings, mockGoogleMapsDurationGetter);

		String expectedMessage = String.format("I should arrive in approximately %s. My current location is: http://maps.google.co.uk/maps?q=%s,%s. Update sent by En Route!", duration, originLat, originLong);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong);
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testThatMessageIsAsExpectedWhenBothTimeEstimateButNotMapLinkIsRequired() throws UnableToGetEstimatedJourneyTimeException {
		when(mockUpdaterSettings.isIncludeMapsLink()).thenReturn(false);
		when(mockGoogleMapsDurationGetter.getEstimatedJourneyTime(anyString(), anyString(), anyString(), any(ModeOfTransport.class))).thenReturn(duration);
		MessageGenerator messageGenerator = new MessageGenerator(mockUpdaterSettings, mockGoogleMapsDurationGetter);

		String expectedMessage = String.format("I should arrive in approximately %s. Update sent by En Route!", duration);
		String actualMessage = messageGenerator.generateMessage(originLat, originLong);
		assertEquals(expectedMessage, actualMessage);
	}
}
