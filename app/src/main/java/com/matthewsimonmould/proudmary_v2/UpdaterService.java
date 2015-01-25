package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
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
import com.matthewsimonmould.proudmary_v2.settings.UpdaterSettings;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UpdaterService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {

	private long retryIfFailDelay = TimeUnit.MINUTES.toMillis(5);
	private String debugTag;
	private GoogleApiClient mGoogleApiClient;

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
		return 0;
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
		AsyncTask<Void, Void, Void> asyncUpdateTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				Notifier notifier = new Notifier(getApplicationContext());
				UpdaterSettings updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));
				Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

				if (lastLocation != null) {
					GoogleMapsDurationGetter googleMapsDurationGetter = new GoogleMapsDurationGetter(new UrlAccessor());
					MessageGenerator messageGenerator = new MessageGenerator(googleMapsDurationGetter);
					String message = messageGenerator.generateMessage(
							String.valueOf(lastLocation.getLatitude()),
							String.valueOf(lastLocation.getLongitude()),
							updaterSettings.getDestination());

					SMSSender.sendSMS(updaterSettings.getRecipient(), message);

					notifier.notify(getApplicationContext().getResources().getString(R.string.update_sent_notification));

					scheduleNextUpdate(updaterSettings.getUpdatePeriodInMillis());
				} else {
					notifier.notify(getApplicationContext().getResources().getString(R.string.update_could_not_be_sent));

					scheduleNextUpdate(retryIfFailDelay);
				}
				return null;
			}
		};

		asyncUpdateTask.execute();
	}

	private void scheduleNextUpdate(Long delay) {
		Intent intent = new Intent(getApplicationContext(), SendUpdateBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);

		alarmManager.set(AlarmManager.RTC_WAKEUP, new Date().getTime() + delay, pendingIntent);
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
