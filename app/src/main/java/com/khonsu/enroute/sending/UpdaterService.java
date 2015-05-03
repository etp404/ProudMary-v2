package com.khonsu.enroute.sending;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.usernotifications.Notifier;

public class UpdaterService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {

	private GoogleApiClient mGoogleApiClient;

	@Override
	public void onCreate() {
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
		sendUpdate();
	}

	private void sendUpdate() {

		final UpdaterSettings updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));

		AsyncUpdateTask asyncUpdateTask = new AsyncUpdateTask(
				updaterSettings,
				Notifier.getInstance(getApplicationContext()),
				mGoogleApiClient,
				new UpdateScheduler(getApplicationContext()));

		//This is to alleviate a weird bug where messages randomly started being sent when the service should have been off!
		if (updaterSettings.isUpdatesActive()) {
			asyncUpdateTask.execute();
		}
	}

	@Override
	public void onConnectionSuspended(int i) {
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	}
}
