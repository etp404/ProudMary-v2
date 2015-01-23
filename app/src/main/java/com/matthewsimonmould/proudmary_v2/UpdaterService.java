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

import java.util.Date;

public class UpdaterService extends Service implements ConnectionCallbacks, OnConnectionFailedListener {

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
        }
        else {
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
		AsyncTask<Void, Void, Void> ayncUpdateTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				UpdaterSettings updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));
				Location lastLocation =
						LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
				if (lastLocation != null) {
					GoogleMapsDurationGetter googleMapsDurationGetter = new GoogleMapsDurationGetter(new UrlAccessor());
					String estimatedDuration =
							googleMapsDurationGetter.getEstimatedJourneyTime(
									String.valueOf(lastLocation.getLatitude()),
									String.valueOf(lastLocation.getLongitude()),
									updaterSettings.getDestination());

					String message = MessageGenerator.generateMessage(lastLocation, estimatedDuration);
					SMSSender.sendSMS(updaterSettings.getRecipient(), message);
				}
				new Notifier(getApplicationContext()).notify(getApplicationContext().getResources().getString(R.string.update_sent_notification));

				Intent intent = new Intent(getApplicationContext(), SendUpdateBroadcastReceiver.class);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

				AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(pendingIntent);

				alarmManager.set(AlarmManager.RTC_WAKEUP, new Date().getTime() + updaterSettings.getUpdatePeriodInMillis(), pendingIntent);

				return null;
			}
		};
		ayncUpdateTask.execute();
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
