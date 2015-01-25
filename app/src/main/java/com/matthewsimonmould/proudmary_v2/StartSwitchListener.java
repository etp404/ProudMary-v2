package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.matthewsimonmould.proudmary_v2.settings.StoredUpdateSetting;
import com.matthewsimonmould.proudmary_v2.settings.UpdaterSettings;
import com.matthewsimonmould.proudmary_v2.uifields.DestinationTextField;
import com.matthewsimonmould.proudmary_v2.uifields.FrequencyNumberPicker;
import com.matthewsimonmould.proudmary_v2.uifields.RecipientTextField;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private final Context context;
	private final StoredUpdateSetting storedUpdateSetting;
	private final UpdaterSettings updaterSettings;
	private final FrequencyNumberPicker frequencyNumberPicker;
	private final RecipientTextField recipientTextField;
	private final DestinationTextField destinationTextField;
	private final ImageButton contactPickerButton;

	public StartSwitchListener(Context context,
							   StoredUpdateSetting storedUpdateSetting,
							   UpdaterSettings updaterSettings,
							   DestinationTextField destinationTextField,
							   FrequencyNumberPicker frequencyNumberPicker,
							   RecipientTextField recipientTextField,
							   ImageButton contactPickerButton) {
        this.context = context;
		this.storedUpdateSetting = storedUpdateSetting;
		this.updaterSettings = updaterSettings;
		this.frequencyNumberPicker = frequencyNumberPicker;
		this.destinationTextField = destinationTextField;
		this.recipientTextField = recipientTextField;
		this.contactPickerButton = contactPickerButton;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//TODO: validate form
		Intent updateServiceIntent = new Intent(context, UpdaterService.class);

        if (isChecked) {
			if (validateForm()) {
				context.startService(updateServiceIntent);
				updaterSettings.setUpdatePeriodInMinutes(frequencyNumberPicker.getUpdateInMinutes());
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
		frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		contactPickerButton.setEnabled(!storedUpdateSetting.isUpdatesActive());
		contactPickerButton.setImageAlpha(storedUpdateSetting.isUpdatesActive() ? 100: 255);
    }

	private boolean validateForm() {
		if (!PhoneNumberValidator.isValidPhoneNumber(recipientTextField.getRecipientNumber())) {
			recipientTextField.highlightError();
			return false;
		}
		return true;
	}
}
