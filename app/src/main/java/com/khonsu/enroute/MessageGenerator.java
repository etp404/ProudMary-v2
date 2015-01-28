package com.khonsu.enroute;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class MessageGenerator {
	static final String MESSAGE_ESTIMATE_FORMAT = "I should arrive in approximately %s.";
	static final String CURRENT_LOCATION_ESTIMATE_FORMAT = "My current location is: %s";
	static final String COULD_NOT_UPDATE_ETA = "Could not obtain ETA. Please check destination value and internet connection.";
	private final GoogleMapsDurationGetter googleMapsDurationGetter;

	public MessageGenerator(GoogleMapsDurationGetter googleMapsDurationGetter) {
		this.googleMapsDurationGetter = googleMapsDurationGetter;
	}

	public String generateMessage(Context context, String currentLatitude, String currentLongitude, String destination) {
		StringBuffer message = new StringBuffer();
		appendEstimateIfPossible(context, currentLatitude, currentLongitude, destination, message);
		appendCurrentLocationLink(currentLatitude, currentLongitude, message);
		return message.toString();
	}

	private void appendCurrentLocationLink(String currentLatitude, String currentLongitude, StringBuffer message) {
		if (message.length()>0) {
			message.append(" ");
		}
		message.append(String.format(CURRENT_LOCATION_ESTIMATE_FORMAT, GoogleMapsLinkGenerator.generateLinkForLatLong(currentLatitude, currentLongitude)));
	}

	private void appendEstimateIfPossible(final Context context, String currentLatitude, String currentLongitude, String destination, StringBuffer message) {
		try {
			message.append(String.format(MESSAGE_ESTIMATE_FORMAT, googleMapsDurationGetter.getEstimatedJourneyTime(currentLatitude, currentLongitude, destination)));
		} catch (UnableToGetEstimatedJourneyTimeException e) {
			e.printStackTrace();

			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, COULD_NOT_UPDATE_ETA, Toast.LENGTH_LONG).show();
				}
			});
		}
	}
}
