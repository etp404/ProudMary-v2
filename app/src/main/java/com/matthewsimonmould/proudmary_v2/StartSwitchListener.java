package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private final Context context;
	private final StoredUpdateSetting storedUpdateSetting;
	private final UpdatePeriodInMinutesSetting updatePeriodInMinutesSetting;
	private final FrequencyTextField frequency;

    public StartSwitchListener(Context context, StoredUpdateSetting storedUpdateSetting, UpdatePeriodInMinutesSetting updatePeriodInMinutesSetting, FrequencyTextField frequency) {
        this.context = context;
		this.storedUpdateSetting = storedUpdateSetting;
		this.updatePeriodInMinutesSetting = updatePeriodInMinutesSetting;
		this.frequency = frequency;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//TODO: validate form

		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (isChecked) {
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, frequency.getUpdateInMillis(), pendingIntent);
			updatePeriodInMinutesSetting.setUpdatePeriodInMinutes(frequency.getUpdateInMinutes());
			storedUpdateSetting.setUpdatesActive(true);
        }
        else {
            alarmManager.cancel(pendingIntent);
			updatePeriodInMinutesSetting.deleteRecord();
			storedUpdateSetting.setUpdatesActive(false);
		}
		frequency.setEnabledOrDisabledAccordingToUpdateStatus();
    }
}
