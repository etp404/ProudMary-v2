package com.khonsu.enroute.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
* Created by mouldm02 on 21/03/2015.
*/
public class NetworkStatus {
    private Context context;

    public NetworkStatus(Context context) {
        this.context = context;
    }

    public boolean isAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
