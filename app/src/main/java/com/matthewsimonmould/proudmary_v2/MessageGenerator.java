package com.matthewsimonmould.proudmary_v2;

import android.location.Location;

public class MessageGenerator {
	static final String MESSAGE_FORMAT = "My current location is: %s";

	public static String generateMessage(Location location) {
		return String.format(MESSAGE_FORMAT, GoogleMapsLinkGenerator.generateLinkForLongLat(location.getLatitude(), location.getLongitude()));
	}
}
