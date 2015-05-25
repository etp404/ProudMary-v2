package com.khonsu.enroute.settingup;

import android.content.Context;
import android.content.Intent;

import com.khonsu.enroute.sending.SendUpdateBroadcastReceiver;

public final class BroadcastSender {
	private Context context;

	public BroadcastSender(Context context) {
		this.context = context;
	}

	public void sendUpdate() {
		context.sendBroadcast(new Intent(context, SendUpdateBroadcastReceiver.class));
	}

	public void stopUpdates() {
		context.sendBroadcast(new Intent(context, TurnOffUpdatesReceiver.class));
	}
}
