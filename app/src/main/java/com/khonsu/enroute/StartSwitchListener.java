package com.khonsu.enroute;

import android.content.Context;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.DestinationTextField;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.TextFieldWrapper;

import java.util.ArrayList;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private static final String NO_INTERNET_CONNECTION = "Please ensure network is enabled.";
	private final Context context;
	private final UpdaterSettings updaterSettings;
	private final FrequencyNumberPicker frequencyNumberPicker;
	private final TextFieldWrapper recipientTextField;
	private final DestinationTextField destinationTextField;
	private final ImageButton contactPickerButton;
	private final NetworkStatusProvider networkStatusProvider;

	public StartSwitchListener(Context context,
							   UpdaterSettings updaterSettings,
							   DestinationTextField destinationTextField,
							   FrequencyNumberPicker frequencyNumberPicker,
							   TextFieldWrapper recipientTextField,
							   ImageButton contactPickerButton,
							   NetworkStatusProvider networkStatusProvider) {
        this.context = context;
		this.updaterSettings = updaterSettings;
		this.frequencyNumberPicker = frequencyNumberPicker;
		this.destinationTextField = destinationTextField;
		this.recipientTextField = recipientTextField;
		this.contactPickerButton = contactPickerButton;
		this.networkStatusProvider = networkStatusProvider;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//TODO: validate form
		Intent updateServiceIntent = new Intent(context, UpdaterService.class);

        if (isChecked && validateNetworkAvailable()) {
			AsynchronousFormValidator asychronousFormValidator = new AsynchronousFormValidator(this, buttonView, updateServiceIntent);
			asychronousFormValidator.execute(new ArrayList<TextFieldWrapper>(){{
				add(recipientTextField);
			}});
        }
        else {
			onFormValidateFailure(buttonView);
			updaterSettings.setUpdatesActive(false);
			UpdateScheduler.cancelUpdate(context);
			updateButtonStates();
		}
    }

	private boolean validateNetworkAvailable() {
		boolean networkState = networkStatusProvider.networkAvailable();
		if (!networkState) {
			Toast.makeText(context, NO_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
		}
		return networkState;
	}

	public void updateButtonStates() {
		frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		contactPickerButton.setEnabled(!updaterSettings.isUpdatesActive());
		contactPickerButton.setImageAlpha(updaterSettings.isUpdatesActive() ? 100: 255); //TODO: pull the contact picker into own class.
	}

	public void onFormValidateFailure(CompoundButton buttonView) {
		buttonView.setChecked(false);
	}

}
