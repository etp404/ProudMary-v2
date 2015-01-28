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
		String expectedMessage = "My current location is: https://www.google.co.uk/maps/@16.4,-2.2,10z";

		MessageGenerator messageGenerator = new MessageGenerator(new ErrorThrowingGoogleMapsDurationGetter(null));
		String actualMessage = messageGenerator.generateMessage(Robolectric.application, "16.4", "-2.2", "Destination");

		assertEquals(expectedMessage, actualMessage);
		assertEquals("Could not obtain ETA. Please check destination value and internet connection.", ShadowToast.getTextOfLatestToast());
	}

	@Test
	public void testThatDurationEstimateIsIncludedIfPossible() throws MalformedURLException, UnableToGetEstimatedJourneyTimeException {
		String duration = "2.5 hours";
		String destination = "Edinburgh";
		String originLat = "16";
		String originLong = "2";

		String expectedMessage = String.format("I should arrive in approximately %s. My current location is: https://www.google.co.uk/maps/@%s,%s,10z", duration, originLat, originLong);

		FakeGoogleMapsDurationGetter fakeGoogleMapsDurationGetter = new FakeGoogleMapsDurationGetter(originLat, originLong, destination, duration);
		fakeGoogleMapsDurationGetter.duration = duration;

		MessageGenerator messageGenerator = new MessageGenerator(fakeGoogleMapsDurationGetter);
		String actualMessage = messageGenerator.generateMessage(Robolectric.application, originLat, originLong, destination);
		assertEquals(expectedMessage, actualMessage);
	}

	private class ErrorThrowingGoogleMapsDurationGetter extends GoogleMapsDurationGetter {
		public ErrorThrowingGoogleMapsDurationGetter(UrlAccessor urlAccessor) {
			super(urlAccessor);
		}

		@Override
		public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination) throws UnableToGetEstimatedJourneyTimeException {
			throw new UnableToGetEstimatedJourneyTimeException();
		}
	}

	private class FakeGoogleMapsDurationGetter extends GoogleMapsDurationGetter {

		String duration;
		private String mockedForLat;
		private String mockedForLong;
		private String mockedForDestination;

		public FakeGoogleMapsDurationGetter(String mockedForLat, String mockedForLong, String mockedForDestination, String duration) {
			super(null);
			this.mockedForLat = mockedForLat;
			this.mockedForLong = mockedForLong;
			this.mockedForDestination = mockedForDestination;
			this.duration = duration;
		}

		public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination) throws UnableToGetEstimatedJourneyTimeException {
			if (currentLat.equals(mockedForLat) && currentLong.equals(mockedForLong) && destination.equals(mockedForDestination)) {
				return duration;
			}
			return null;
		}
	}
}
