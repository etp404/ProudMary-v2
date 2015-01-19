package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;

import com.matthewsimonmould.proudmary_v2.uifields.DestinationTextField;
import com.matthewsimonmould.proudmary_v2.uifields.FrequencyTextField;
import com.matthewsimonmould.proudmary_v2.uifields.RecipientTextField;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private final Context context;
	private final StoredUpdateSetting storedUpdateSetting;
	private final UpdaterSettings updaterSettings;
	private final FrequencyTextField frequencyTextField;
	private final RecipientTextField recipientTextField;
	private final DestinationTextField destinationTextField;

	public StartSwitchListener(Context context,
							   StoredUpdateSetting storedUpdateSetting,
							   UpdaterSettings updaterSettings,
							   DestinationTextField destinationTextField,
							   FrequencyTextField frequencyTextField,
							   RecipientTextField recipientTextField) {
        this.context = context;
		this.storedUpdateSetting = storedUpdateSetting;
		this.updaterSettings = updaterSettings;
		this.frequencyTextField = frequencyTextField;
		this.destinationTextField = destinationTextField;
		this.recipientTextField = recipientTextField;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//TODO: validate form

		Intent intent = new Intent(context, SendUpdateBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (isChecked) {
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, frequencyTextField.getUpdateInMillis(), pendingIntent);
			updaterSettings.setUpdatePeriodInMinutes(frequencyTextField.getUpdateInMinutes());
			updaterSettings.setRecipient(recipientTextField.getRecipientNumber());
			updaterSettings.setDestination(destinationTextField.getDestination());
			storedUpdateSetting.setUpdatesActive(true);
        }
        else {
            alarmManager.cancel(pendingIntent);
			storedUpdateSetting.setUpdatesActive(false);
		}
		frequencyTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
    }
}
