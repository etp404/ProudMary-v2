package com.khonsu.enroute.util;

import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.sending.UpdaterService;

public final class Navigator {
	private Context context;

	public Navigator(Context context) {
		this.context = context;
	}

	public void startUpdateService() {
		Intent updateServiceIntent = new Intent(context, UpdaterService.class);
		context.startService(updateServiceIntent);
	}
}
