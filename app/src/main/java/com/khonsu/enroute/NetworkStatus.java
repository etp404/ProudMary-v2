package com.khonsu.enroute;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
* Created by mouldm02 on 21/03/2015.
*/
public class NetworkStatus {
    private static final String NO_INTERNET_CONNECTION = "Please ensure network is enabled.";

    private Context context;

    public NetworkStatus(Context context) {
        this.context = context;
    }

    public boolean isAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean networkState = netInfo != null && netInfo.isConnected();
        if (!networkState) {
            Toast.makeText(context, NO_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
        }
        return networkState;
    }
}
