package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;

import java.util.concurrent.TimeUnit;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private final StoredUpdateSetting storedUpdateSetting;
	private Context context;

    public StartSwitchListener(Context context, StoredUpdateSetting storedUpdateSetting) {
        this.context = context;
		this.storedUpdateSetting = storedUpdateSetting;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (isChecked) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, TimeUnit.SECONDS.toMillis(30), pendingIntent);
			storedUpdateSetting.setUpdatesActive(true);
        }
        else {
            alarmManager.cancel(pendingIntent);
			storedUpdateSetting.setUpdatesActive(false);
		}
    }
}
