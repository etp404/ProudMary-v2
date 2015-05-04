package com.khonsu.enroute.sending.messaging;

import com.khonsu.enroute.DistanceFormatter;
import com.khonsu.enroute.Estimate;
import com.khonsu.enroute.GoogleMapsDurationGetter;
import com.khonsu.enroute.GoogleMapsLinkGenerator;
import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.UnableToGetEstimatedJourneyTimeException;
import com.khonsu.enroute.settings.UpdaterSettings;

public class MessageGenerator {
	private static final String MESSAGE_DESTINATION_FORMAT = "I am en route to %s.";
	private static final String MESSAGE_DURATION_ESTIMATE_FORMAT = "I should arrive in approximately %s.";
	private static final String CURRENT_LOCATION_ESTIMATE_FORMAT = "My current location is: %s.";
	private static final String MESSAGE_DISTANCE_FORMAT = "I am %s away.";
	private static final String PLUG=" Update sent by En Route!";

	private UpdaterSettings updaterSettings;

	public MessageGenerator(UpdaterSettings updaterSettings) {
		this.updaterSettings = updaterSettings;
	}

	public String generateMessage(String currentLatitude, String currentLongitude, Estimate estimate) {
		StringBuffer message = new StringBuffer();
		if (updaterSettings.isIncludeDestinationInMessage()) {
			appendDestination(message, updaterSettings.getDestination());
		}
		appendDurationEstimate(message, estimate.duration);
		if (updaterSettings.isIncludeDistanceInMessage()) {
			appendDistanceEstimate(message, estimate.distance);
		}
		if (updaterSettings.isIncludeMapsLink()) {
			appendCurrentLocationLink(currentLatitude, currentLongitude, message);
		}
        message.append(PLUG);
		return message.toString();
	}

	private void appendDestination(StringBuffer message, String destination) {
		appendSpaceIfNecessary(message);
		message.append(String.format(MESSAGE_DESTINATION_FORMAT, destination));
	}

	private void appendDurationEstimate(StringBuffer message, String duration) {
		appendSpaceIfNecessary(message);
		message.append(String.format(MESSAGE_DURATION_ESTIMATE_FORMAT, duration));
	}

	private void appendDistanceEstimate(StringBuffer message, String distanceInMetres) {
		appendSpaceIfNecessary(message);
		message.append(String.format(MESSAGE_DISTANCE_FORMAT, DistanceFormatter.formatMetersAsMiles(distanceInMetres)));
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
