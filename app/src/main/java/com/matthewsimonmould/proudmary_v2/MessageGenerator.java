package com.matthewsimonmould.proudmary_v2;

import android.location.Location;

public class MessageGenerator {
	static final String MESSAGE_FORMAT = "I should arrive in approximately %s. My current location is: %s";

	public static String generateMessage(Location location, String estimatedDuration) {
		return String.format(MESSAGE_FORMAT, estimatedDuration, GoogleMapsLinkGenerator.generateLinkForLongLat(location.getLatitude(), location.getLongitude()));
	}
}
