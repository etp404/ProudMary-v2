package com.khonsu.enroute;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.CompoundButton;

import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.TextFieldWrapper;

import java.util.List;

class AsynchronousFormValidator extends AsyncTask<List<TextFieldWrapper>, Void, Boolean> {

	private StartSwitchListener startSwitchListener;
	private UpdaterSettings updaterSettings;
	private CompoundButton buttonView;
	private Intent intent;

	public AsynchronousFormValidator(StartSwitchListener startSwitchListener,
									 UpdaterSettings updaterSettings,
									 CompoundButton buttonView,
									 Intent intent) {
		this.startSwitchListener = startSwitchListener;
		this.updaterSettings = updaterSettings;
		this.buttonView = buttonView;
		this.intent = intent;
	}
	@Override
	protected Boolean doInBackground(List<TextFieldWrapper>... params) {
		return validateForm(params[0]);
	}

	@Override
	protected void onPostExecute(Boolean validatedSuccessfully) {
		if (validatedSuccessfully) {
			onFormValidateSuccess(intent);
		}
		else {
			startSwitchListener.onFormValidateFailure(buttonView);
		}
		startSwitchListener.updateButtonStates();
	}

	public boolean validateForm(List<TextFieldWrapper> textFieldWrappers) {

		boolean formValid = true;
		for (TextFieldWrapper textFieldWrapper : textFieldWrappers) {
			if(!textFieldWrapper.validate()) {
				formValid = false;
			}
		}
		return formValid;
	}

	public void onFormValidateSuccess(Intent updateServiceIntent) {
		updaterSettings.setUpdatePeriodInMinutes(frequencyNumberPicker.getUpdateInMinutes());
		updaterSettings.setRecipient(recipientTextField.getContent());
		updaterSettings.setDestination(destinationTextField.getDestination());
		updaterSettings.setUpdatesActive(true);
		context.startService(updateServiceIntent);
	}
}
