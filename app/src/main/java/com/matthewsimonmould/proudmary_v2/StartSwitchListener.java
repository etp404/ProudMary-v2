package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;

import com.matthewsimonmould.proudmary_v2.settings.StoredUpdateSetting;
import com.matthewsimonmould.proudmary_v2.settings.UpdaterSettings;
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


		Intent updateServiceIntent = new Intent(context, UpdaterService.class);

        if (isChecked) {
			if (validateForm()) {
				context.startService(updateServiceIntent);
				updaterSettings.setUpdatePeriodInMinutes(frequencyTextField.getUpdateInMinutes());
				updaterSettings.setRecipient(recipientTextField.getRecipientNumber());
				updaterSettings.setDestination(destinationTextField.getDestination());
				storedUpdateSetting.setUpdatesActive(true);
			}
			else {
				buttonView.setChecked(false);
			}
        }
        else {
			Intent broadcastReceiverIntent = new Intent(context, SendUpdateBroadcastReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, broadcastReceiverIntent, 0);
			AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(pendingIntent);
			storedUpdateSetting.setUpdatesActive(false);
		}
		frequencyTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
    }

	private boolean validateForm() {
		if (!PhoneNumberValidator.isValidPhoneNumber(recipientTextField.getRecipientNumber())) {
			recipientTextField.highlightError();
			return false;
		}
		return true;
	}
}
