package com.khonsu.enroute.messaging;

import com.khonsu.enroute.GoogleMapsDurationGetter;
import com.khonsu.enroute.GoogleMapsLinkGenerator;
import com.khonsu.enroute.UnableToGetEstimatedJourneyTimeException;

public class MessageGenerator {
	static final String MESSAGE_ESTIMATE_FORMAT = "I should arrive in approximately %s.";
	static final String CURRENT_LOCATION_ESTIMATE_FORMAT = "My current location is: %s.";
    static final String PLUG=" Update sent by En Route!";

	private final GoogleMapsDurationGetter googleMapsDurationGetter;

	public MessageGenerator(GoogleMapsDurationGetter googleMapsDurationGetter) {
		this.googleMapsDurationGetter = googleMapsDurationGetter;
	}

	public String generateMessage(String currentLatitude, String currentLongitude, String destination, String mode) throws UnableToGetEstimatedJourneyTimeException {
		StringBuffer message = new StringBuffer();
		appendEstimate(currentLatitude, currentLongitude, destination, mode, message);
		appendCurrentLocationLink(currentLatitude, currentLongitude, message);
        message.append(PLUG);
		return message.toString();
	}

	private void appendCurrentLocationLink(String currentLatitude, String currentLongitude, StringBuffer message) {
		if (message.length()>0) {
			message.append(" ");
		}
		message.append(String.format(CURRENT_LOCATION_ESTIMATE_FORMAT, GoogleMapsLinkGenerator.generateLinkForLatLong(currentLatitude, currentLongitude)));
	}

	private void appendEstimate(String currentLatitude, String currentLongitude, String destination, String mode, StringBuffer message) throws UnableToGetEstimatedJourneyTimeException {
			message.append(String.format(MESSAGE_ESTIMATE_FORMAT, googleMapsDurationGetter.getEstimatedJourneyTime(currentLatitude, currentLongitude, destination, mode)));
	}
}
