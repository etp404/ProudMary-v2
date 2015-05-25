package com.khonsu.enroute.sending;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class LocationGetter implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

	private GoogleApiClient googleApiClient;
	private LocationCallback locationCallback;

	public void getLocation(Context context, LocationCallback locationCallback) {

		this.locationCallback = locationCallback;

		googleApiClient = new GoogleApiClient.Builder(context)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();

		if (googleApiClient.isConnected()) {
			locationCallback.success(LocationServices.FusedLocationApi.getLastLocation(googleApiClient));
		} else {
			googleApiClient.connect();
		}
	}


	@Override
	public void onConnected(Bundle bundle) {

		if (locationCallback != null) {
			locationCallback.success(LocationServices.FusedLocationApi.getLastLocation(googleApiClient));
		}
	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}

	public interface LocationCallback {
		void success(Location location);
	}
}
