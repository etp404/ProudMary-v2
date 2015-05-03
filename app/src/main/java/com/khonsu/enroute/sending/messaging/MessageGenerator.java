package com.khonsu.enroute.sending.messaging;

import com.khonsu.enroute.GoogleMapsDurationGetter;
import com.khonsu.enroute.GoogleMapsLinkGenerator;
import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.UnableToGetEstimatedJourneyTimeException;
import com.khonsu.enroute.settings.UpdaterSettings;

public class MessageGenerator {
	private static final String MESSAGE_DURATION_ESTIMATE_FORMAT = "I should arrive in approximately %s.";
	private static final String CURRENT_LOCATION_ESTIMATE_FORMAT = "My current location is: %s.";
	private static final String MESSAGE_DISTANCE_FORMAT = "I am %s away.";
	private static final String PLUG=" Update sent by En Route!";

	private UpdaterSettings updaterSettings;
	private final GoogleMapsDurationGetter googleMapsDurationGetter;

	public MessageGenerator(UpdaterSettings updaterSettings, GoogleMapsDurationGetter googleMapsDurationGetter) {
		this.updaterSettings = updaterSettings;
		this.googleMapsDurationGetter = googleMapsDurationGetter;
	}

	public String generateMessage(String currentLatitude, String currentLongitude) throws UnableToGetEstimatedJourneyTimeException {
		StringBuffer message = new StringBuffer();
		appendDurationEstimate(currentLatitude, currentLongitude, updaterSettings.getDestination(), updaterSettings.getTransportMode(), message);
		if (updaterSettings.isIncludeDistance()) {
			appendDistanceEstimate(currentLatitude, currentLongitude, updaterSettings.getDestination(), updaterSettings.getTransportMode(), message);
		}
		if (updaterSettings.isIncludeMapsLink()) {
			appendCurrentLocationLink(currentLatitude, currentLongitude, message);
		}
        message.append(PLUG);
		return message.toString();
	}

	private void appendDurationEstimate(String currentLatitude, String currentLongitude, String destination, ModeOfTransport mode, StringBuffer message) throws UnableToGetEstimatedJourneyTimeException {
		message.append(String.format(MESSAGE_DURATION_ESTIMATE_FORMAT, googleMapsDurationGetter.getJourneyEstimate(currentLatitude, currentLongitude, destination, mode).duration));
	}

	private void appendDistanceEstimate(String currentLatitude, String currentLongitude, String destination, ModeOfTransport mode, StringBuffer message) throws UnableToGetEstimatedJourneyTimeException {
		appendSpaceIfNecessary(message);
		message.append(String.format(MESSAGE_DISTANCE_FORMAT, googleMapsDurationGetter.getJourneyEstimate(currentLatitude, currentLongitude, destination, mode).distance));
	}

	private void appendCurrentLocationLink(String currentLatitude, String currentLongitude, StringBuffer message) {
		appendSpaceIfNecessary(message);
		message.append(String.format(CURRENT_LOCATION_ESTIMATE_FORMAT, GoogleMapsLinkGenerator.generateLinkForLatLong(currentLatitude, currentLongitude)));
	}

	private void appendSpaceIfNecessary(StringBuffer message) {
		if (message.length()>0) {
			message.append(" ");
		}
	}

}
