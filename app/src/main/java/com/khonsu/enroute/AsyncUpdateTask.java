package com.khonsu.enroute;

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.khonsu.enroute.contactsautocomplete.Contact;
import com.khonsu.enroute.messaging.MessageGenerator;
import com.khonsu.enroute.messaging.SMSSender;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AsyncUpdateTask extends AsyncTask<Void, Void, Void> {
	private static long RETRY_IF_FAIL_DELAY = TimeUnit.MINUTES.toMillis(5);

	private final Notifier notifier;
	private final Contact recipient;
	private final String destination;
	private final ModeOfTransport modeOfTransport;
	private final long updatePeriodInMillis;
	private final GoogleApiClient googleApiClient;

	public AsyncUpdateTask(Notifier notifier, Contact recipient, String destination, ModeOfTransport modeOfTransport, long updatePeriodInMillis, GoogleApiClient googleApiClient) {
		this.notifier = notifier;
		this.recipient = recipient;
		this.destination = destination;
		this.modeOfTransport = modeOfTransport;
		this.updatePeriodInMillis = updatePeriodInMillis;
		this.googleApiClient = googleApiClient;
	}

	@Override
	protected Void doInBackground(Void... params) {
		Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

		if (lastLocation == null) {
			notifier.notifyLocationUnavailable();
			UpdateScheduler.scheduleNextUpdate(new Date().getTime() + RETRY_IF_FAIL_DELAY);
			return null;
		}
		GoogleMapsDurationGetter googleMapsDurationGetter = new GoogleMapsDurationGetter(new UrlAccessor());
		MessageGenerator messageGenerator = new MessageGenerator(googleMapsDurationGetter);
		try {
			String message = messageGenerator.generateMessage(
					String.valueOf(lastLocation.getLatitude()),
					String.valueOf(lastLocation.getLongitude()),
					destination,
					modeOfTransport);
			SMSSender.sendSMS(recipient.getNumber(), message);

			notifier.notifyUpdateSent();

			UpdateScheduler.scheduleNextUpdate(new Date().getTime() + updatePeriodInMillis);
		} catch (UnableToGetEstimatedJourneyTimeException e) {
			e.printStackTrace();
			notifier.notifyUnableToGetEta();
		}
		return null;
	}
}
