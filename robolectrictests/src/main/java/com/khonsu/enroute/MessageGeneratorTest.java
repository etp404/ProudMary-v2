package com.khonsu.enroute;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MessageGeneratorTest {

	@Test
	public void testThatOnlyCurrentLocationIsIncludedInTheMessageIfItIsAllThatIsAvailable() throws UnableToGetEstimatedJourneyTimeException {
		String expectedMessage = "My current location is: http://maps.google.co.uk/maps?q=16.4,-2.2. Update sent by En Route!";

		MessageGenerator messageGenerator = new MessageGenerator(new ErrorThrowingGoogleMapsDurationGetter(null));
		String actualMessage = messageGenerator.generateMessage(Robolectric.application, "16.4", "-2.2", "Destination", "cycling");

		assertEquals(expectedMessage, actualMessage);
		assertEquals("Could not obtain ETA. Please check destination value and internet connection.", ShadowToast.getTextOfLatestToast());
	}

	@Test
	public void testThatDurationEstimateIsIncludedIfPossible() throws MalformedURLException, UnableToGetEstimatedJourneyTimeException {
		String duration = "2.5 hours";
		String destination = "Edinburgh";
		String originLat = "16";
		String originLong = "2";
        String mode = "bicycling";

		String expectedMessage = String.format("I should arrive in approximately %s. My current location is: http://maps.google.co.uk/maps?q=%s,%s. Update sent by En Route!", duration, originLat, originLong);

		FakeGoogleMapsDurationGetter fakeGoogleMapsDurationGetter = new FakeGoogleMapsDurationGetter(originLat, originLong, destination, duration, mode);
		fakeGoogleMapsDurationGetter.duration = duration;

		MessageGenerator messageGenerator = new MessageGenerator(fakeGoogleMapsDurationGetter);
		String actualMessage = messageGenerator.generateMessage(Robolectric.application, originLat, originLong, destination, mode);
		assertEquals(expectedMessage, actualMessage);
	}

	private class ErrorThrowingGoogleMapsDurationGetter extends GoogleMapsDurationGetter {
		public ErrorThrowingGoogleMapsDurationGetter(UrlAccessor urlAccessor) {
			super(urlAccessor);
		}

		@Override
		public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination, String mode) throws UnableToGetEstimatedJourneyTimeException {
			throw new UnableToGetEstimatedJourneyTimeException();
		}
	}

	private class FakeGoogleMapsDurationGetter extends GoogleMapsDurationGetter {

		String duration;
		private String mockedForLat;
		private String mockedForLong;
		private String mockedForDestination;
		private String mockedForMode;

		public FakeGoogleMapsDurationGetter(String mockedForLat, String mockedForLong, String mockedForDestination, String duration, String mockedForMode) {
			super(null);
			this.mockedForLat = mockedForLat;
			this.mockedForLong = mockedForLong;
			this.mockedForDestination = mockedForDestination;
            this.mockedForMode = mockedForMode;
			this.duration = duration;
		}

		public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination, String mode) throws UnableToGetEstimatedJourneyTimeException {
			if (currentLat.equals(mockedForLat) && currentLong.equals(mockedForLong) && destination.equals(mockedForDestination) && mode.equals(mockedForMode)) {
				return duration;
			}
			return null;
		}
	}
}
