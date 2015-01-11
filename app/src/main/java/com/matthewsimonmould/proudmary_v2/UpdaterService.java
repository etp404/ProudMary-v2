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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        mGoogleApiClient.connect();
        return 0;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        sendUpdate(lastLocation);
    }

    private void sendUpdate(Location lastLocation) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String message = dateFormat.format(new Date()); //TODO: Create right text
        if (lastLocation != null) {

            message += ". " + GoogleMapsLinkGenerator.generateLinkForLongLat(lastLocation.getLatitude(), lastLocation.getLongitude());
        }

        new Notifier(getApplicationContext()).notify(message);
        SMSSender.sendSMS(message);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("PROUD_MARY", "UpdaterService.onConnectionFailed"); //TODO: handle this.
    }
}
