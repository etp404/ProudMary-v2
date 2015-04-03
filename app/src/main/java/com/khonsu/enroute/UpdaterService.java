package com.khonsu.enroute;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.khonsu.enroute.settings.UpdaterSettings;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdaterService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {

	private long retryIfFailDelay = TimeUnit.MINUTES.toMillis(5);
	private String debugTag;
	private GoogleApiClient mGoogleApiClient;
    private final static Map<Integer, String> MODE_OF_TRANPORT_ID_TO_STRING = new HashMap<Integer, String>(){{
        put(R.id.mode_of_travel_car, "driving");
        put(R.id.mode_of_travel_bike, "bicycling");
        put(R.id.mode_of_travel_walking, "walking");
    }};

	@Override
	public void onCreate() {
		debugTag = getClass().getSimpleName();
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (mGoogleApiClient.isConnected()) {
			sendUpdate();
		} else {
			mGoogleApiClient.connect();
		}
		stopSelf();
		return Service.START_NOT_STICKY;
	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onConnected(Bundle bundle) {
		Log.d(debugTag, "onConnected");
		sendUpdate();
	}

	private void sendUpdate() {
		final UpdaterSettings updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));

		AsyncTask<Void, Void, Void> asyncUpdateTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				Notifier notifier = new Notifier(getApplicationContext());
				Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

				if (lastLocation != null) {
					GoogleMapsDurationGetter googleMapsDurationGetter = new GoogleMapsDurationGetter(new UrlAccessor());
					MessageGenerator messageGenerator = new MessageGenerator(googleMapsDurationGetter);
					String message = messageGenerator.generateMessage(
							getApplicationContext(),
							String.valueOf(lastLocation.getLatitude()),
							String.valueOf(lastLocation.getLongitude()),
							updaterSettings.getDestination(),
                            MODE_OF_TRANPORT_ID_TO_STRING.get(updaterSettings.getTransportMode()));

					SMSSender.sendSMS(updaterSettings.getRecipient().getNumber(), message);

					notifier.notify(getApplicationContext().getResources().getString(R.string.update_sent_notification));

					UpdateScheduler.scheduleNextUpdate(getApplicationContext(), new Date().getTime() + updaterSettings.getUpdatePeriodInMillis(), updaterSettings);
				} else {
					notifier.notify(getApplicationContext().getResources().getString(R.string.update_could_not_be_sent));
					UpdateScheduler.scheduleNextUpdate(getApplicationContext(), new Date().getTime() + retryIfFailDelay, updaterSettings);
				}
				return null;
			}
		};

		//This is to alleviate a weird bug where messages randomly started being sent when the service should have been off!
		if (updaterSettings.isUpdatesActive()) {
			asyncUpdateTask.execute();
		}
	}

	@Override
	public void onConnectionSuspended(int i) {
		Log.d(debugTag, "UpdaterService.onConnectionSuspended"); //TODO: handle this.
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		Log.d(debugTag, "UpdaterService.onConnectionFailed"); //TODO: handle this.
	}
}
