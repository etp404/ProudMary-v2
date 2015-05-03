package com.khonsu.enroute.sending;

import android.location.Location;
import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.khonsu.enroute.Estimate;
import com.khonsu.enroute.GoogleMapsDurationGetter;
import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.usernotifications.Notifier;
import com.khonsu.enroute.UnableToGetEstimatedJourneyTimeException;
import com.khonsu.enroute.UrlAccessor;
import com.khonsu.enroute.sending.messaging.MessageGenerator;
import com.khonsu.enroute.sending.messaging.SMSSender;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AsyncUpdateTask extends AsyncTask<Void, Void, Void> {
	private static long RETRY_IF_FAIL_DELAY = TimeUnit.MINUTES.toMillis(5);

	private final UpdaterSettings updaterSettings;
	private final Notifier notifier;
	private final GoogleApiClient googleApiClient;
	private final UpdateScheduler updateScheduler;

	public AsyncUpdateTask(UpdaterSettings updaterSettings,
						   Notifier notifier,
						   GoogleApiClient googleApiClient,
						   UpdateScheduler updateScheduler) {
		this.updaterSettings = updaterSettings;
		this.notifier = notifier;
		this.googleApiClient = googleApiClient;
		this.updateScheduler = updateScheduler;
	}

	@Override
	protected Void doInBackground(Void... params) {
		Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

		if (lastLocation == null) {
			retrySendingLater();
			return null;
		}
		GoogleMapsDurationGetter googleMapsDurationGetter = new GoogleMapsDurationGetter(new UrlAccessor());
		try {

			Estimate estimate = googleMapsDurationGetter.getJourneyEstimate(
				String.valueOf(lastLocation.getLatitude()),
				String.valueOf(lastLocation.getLongitude()),
				updaterSettings.getDestination(),
				updaterSettings.getTransportMode());

			MessageGenerator messageGenerator = new MessageGenerator(updaterSettings, googleMapsDurationGetter);
			String message = messageGenerator.generateMessage(
					String.valueOf(lastLocation.getLatitude()),
					String.valueOf(lastLocation.getLongitude()),
					estimate);

			SMSSender.sendSMS(updaterSettings.getRecipient().getNumber(), message);

			notifier.notifyUpdateSent();

			long triggerAtMillis = new Date().getTime() + updaterSettings.getUpdatePeriodInMillis();
			updateScheduler.scheduleNextUpdate(triggerAtMillis);
			notifier.notifyNextUpdate(triggerAtMillis);

		} catch (UnableToGetEstimatedJourneyTimeException e) {
			e.printStackTrace();
			retrySendingLater();
		}
		return null;
	}

	private void retrySendingLater() {
		long triggerAtMillis = new Date().getTime() + RETRY_IF_FAIL_DELAY;
		notifier.notifyLocationUnavailable();
		updaterSettings.setTimeForNextUpdateInMillis(triggerAtMillis);
		updateScheduler.scheduleNextUpdate(triggerAtMillis);
		notifier.notifyNextUpdate(triggerAtMillis);
	}
}
