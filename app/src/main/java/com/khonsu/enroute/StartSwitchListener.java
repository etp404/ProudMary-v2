package com.khonsu.enroute;

import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.DestinationTextField;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.RecipientTextField;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private final Context context;
	private final UpdaterSettings updaterSettings;
	private final FrequencyNumberPicker frequencyNumberPicker;
	private final RecipientTextField recipientTextField;
	private final DestinationTextField destinationTextField;
	private final ImageButton contactPickerButton;

	public StartSwitchListener(Context context,
							   UpdaterSettings updaterSettings,
							   DestinationTextField destinationTextField,
							   FrequencyNumberPicker frequencyNumberPicker,
							   RecipientTextField recipientTextField,
							   ImageButton contactPickerButton) {
        this.context = context;
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
				updaterSettings.setUpdatePeriodInMinutes(frequencyNumberPicker.getUpdateInMinutes());
				updaterSettings.setRecipient(recipientTextField.getRecipientNumber());
				updaterSettings.setDestination(destinationTextField.getDestination());
				updaterSettings.setUpdatesActive(true);
				context.startService(updateServiceIntent);
			}
			else {
				buttonView.setChecked(false);
			}
        }
        else {
			updaterSettings.setUpdatesActive(false);
			UpdateScheduler.cancelUpdate(context);
		}
		frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		contactPickerButton.setEnabled(!updaterSettings.isUpdatesActive());
		contactPickerButton.setImageAlpha(updaterSettings.isUpdatesActive() ? 100: 255); //TODO: pull the contact picker into own class.
    }

	private boolean validateForm() {
		if (!recipientTextField.validate()) {
			return false;
		};
		return true;
	}
}
