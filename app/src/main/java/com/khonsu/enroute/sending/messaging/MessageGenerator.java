package com.khonsu.enroute.sending.messaging;

import com.khonsu.enroute.GoogleMapsDurationGetter;
import com.khonsu.enroute.GoogleMapsLinkGenerator;
import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.UnableToGetEstimatedJourneyTimeException;
import com.khonsu.enroute.settings.UpdaterSettings;

public class MessageGenerator {
	static final String MESSAGE_ESTIMATE_FORMAT = "I should arrive in approximately %s.";
	static final String CURRENT_LOCATION_ESTIMATE_FORMAT = "My current location is: %s.";
    static final String PLUG=" Update sent by En Route!";

	private UpdaterSettings updaterSettings;
	private final GoogleMapsDurationGetter googleMapsDurationGetter;

	public MessageGenerator(UpdaterSettings updaterSettings, GoogleMapsDurationGetter googleMapsDurationGetter) {
		this.updaterSettings = updaterSettings;
		this.googleMapsDurationGetter = googleMapsDurationGetter;
	}

	public String generateMessage(String currentLatitude, String currentLongitude) throws UnableToGetEstimatedJourneyTimeException {
		StringBuffer message = new StringBuffer();
		appendEstimate(currentLatitude, currentLongitude, updaterSettings.getDestination(), updaterSettings.getTransportMode(), message);
		if (updaterSettings.isIncludeMapsLink()) {
			appendCurrentLocationLink(currentLatitude, currentLongitude, message);
		}
        message.append(PLUG);
		return message.toString();
	}

	private void appendCurrentLocationLink(String currentLatitude, String currentLongitude, StringBuffer message) {
		appendSpaceIfNecessary(message);
		message.append(String.format(CURRENT_LOCATION_ESTIMATE_FORMAT, GoogleMapsLinkGenerator.generateLinkForLatLong(currentLatitude, currentLongitude)));
	}

	private void appendEstimate(String currentLatitude, String currentLongitude, String destination, ModeOfTransport mode, StringBuffer message) throws UnableToGetEstimatedJourneyTimeException {
			message.append(String.format(MESSAGE_ESTIMATE_FORMAT, googleMapsDurationGetter.getEstimatedJourneyTime(currentLatitude, currentLongitude, destination, mode)));
	}

	private void appendSpaceIfNecessary(StringBuffer message) {
		if (message.length()>0) {
			message.append(" ");
		}
	}

}
