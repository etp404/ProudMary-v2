package com.matthewsimonmould.proudmary_v2;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

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
        Location lastLocation =
                LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
			String message = MessageGenerator.generateMessage(lastLocation);
			SMSSender.sendSMS(message);
		}
		new Notifier(getApplicationContext()).notify(getApplicationContext().getResources().getString(R.string.update_sent_notification));
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
