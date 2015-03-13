package com.khonsu.enroute;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatusProvider {
	private ConnectivityManager connectionManager;

	public NetworkStatusProvider(ConnectivityManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public boolean networkAvailable() {
		NetworkInfo netInfo = connectionManager.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnected();
	}
}
