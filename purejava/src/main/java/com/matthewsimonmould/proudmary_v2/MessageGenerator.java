package com.matthewsimonmould.proudmary_v2;

public class MessageGenerator {
	static final String MESSAGE_ESTIMATE_FORMAT = "I should arrive in approximately %s.";
	static final String CURRENT_LOCATION_ESTIMATE_FORMAT = "My current location is: %s";

	private final GoogleMapsDurationGetter googleMapsDurationGetter;

	public MessageGenerator(GoogleMapsDurationGetter googleMapsDurationGetter) {
		this.googleMapsDurationGetter = googleMapsDurationGetter;
	}

	public String generateMessage(String currentLatitude, String currentLongitude, String destination) {
		StringBuffer message = new StringBuffer();
		appendEstimateIfPossible(currentLatitude, currentLongitude, destination, message);
		appendCurrentLocationLink(currentLatitude, currentLongitude, message);
		return message.toString();
	}

	private void appendCurrentLocationLink(String currentLatitude, String currentLongitude, StringBuffer message) {
		if (message.length()>0) {
			message.append(" ");
		}
		message.append(String.format(CURRENT_LOCATION_ESTIMATE_FORMAT, GoogleMapsLinkGenerator.generateLinkForLongLat(currentLatitude, currentLongitude)));
	}

	private void appendEstimateIfPossible(String currentLatitude, String currentLongitude, String destination, StringBuffer message) {
		try {
			message.append(String.format(MESSAGE_ESTIMATE_FORMAT, googleMapsDurationGetter.getEstimatedJourneyTime(currentLatitude, currentLongitude, destination)));
		} catch (UnableToGetEstimatedJourneyTimeException e) {
			e.printStackTrace();
		}
	}
}
