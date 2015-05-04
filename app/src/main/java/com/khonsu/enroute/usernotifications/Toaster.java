package com.khonsu.enroute.usernotifications;

import android.content.Context;
import android.widget.Toast;

public final class Toaster {
	final Context context;

	public Toaster(Context context) {
		this.context = context;
	}

	public void toast(String toastContent) {
		Toast.makeText(context, toastContent, Toast.LENGTH_LONG).show();
	}
}
