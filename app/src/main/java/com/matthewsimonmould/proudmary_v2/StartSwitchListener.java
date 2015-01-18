package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;

import com.matthewsimonmould.proudmary_v2.uifields.FrequencyTextField;
import com.matthewsimonmould.proudmary_v2.uifields.RecipientTextField;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private final Context context;
	private final StoredUpdateSetting storedUpdateSetting;
	private final UpdaterSettings updaterSettings;
	private final FrequencyTextField frequency;
	private final RecipientTextField recipientTextField;

	public StartSwitchListener(Context context,
							   StoredUpdateSetting storedUpdateSetting,
							   UpdaterSettings updaterSettings,
							   FrequencyTextField frequency,
							   RecipientTextField recipientTextField) {
        this.context = context;
		this.storedUpdateSetting = storedUpdateSetting;
		this.updaterSettings = updaterSettings;
		this.frequency = frequency;
		this.recipientTextField = recipientTextField;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//TODO: validate form

		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (isChecked) {
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, frequency.getUpdateInMillis(), pendingIntent);
			updaterSettings.setUpdatePeriodInMinutes(frequency.getUpdateInMinutes());
			updaterSettings.setRecipient(recipientTextField.getRecipientNumber());
			storedUpdateSetting.setUpdatesActive(true);
        }
        else {
            alarmManager.cancel(pendingIntent);
			updaterSettings.deleteRecord();
			storedUpdateSetting.setUpdatesActive(false);
		}
		frequency.setEnabledOrDisabledAccordingToUpdateStatus();
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
    }
}
