package com.khonsu.enroute;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.TextField;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

	private static final String NO_INTERNET_CONNECTION = "Please ensure network is enabled.";
	private final Context context;
	private final UpdaterSettings updaterSettings;
	private final FrequencyNumberPicker frequencyNumberPicker;
	private final TextField recipientTextField;
	private final TextField destinationTextField;
	private final ImageButton contactPickerButton;

	public StartSwitchListener(Context context,
							   UpdaterSettings updaterSettings,
							   TextField destinationTextField,
							   FrequencyNumberPicker frequencyNumberPicker,
							   TextField recipientTextField,
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

        if (isChecked && validateNetworkAvailable()) {
			AsynchronousFormValidator asychronousFormValidator = new AsynchronousFormValidator(buttonView, updateServiceIntent);
			asychronousFormValidator.execute();
        }
        else {
			onFormValidateFailure(buttonView);
			updaterSettings.setUpdatesActive(false);
			UpdateScheduler.cancelUpdate(context);
			updateButtonStates();
		}
    }

	private boolean validateNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		boolean networkState = netInfo != null && netInfo.isConnected();
		if (!networkState) {
			Toast.makeText(context, NO_INTERNET_CONNECTION, Toast.LENGTH_LONG).show();
		}
		return networkState;
	}

	private void updateButtonStates() {
		frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		contactPickerButton.setEnabled(!updaterSettings.isUpdatesActive());
		contactPickerButton.setImageAlpha(updaterSettings.isUpdatesActive() ? 100: 255); //TODO: pull the contact picker into own class.
	}

	private void onFormValidateFailure(CompoundButton buttonView) {
		buttonView.setChecked(false);
	}

	private void onFormValidateSuccess(Intent updateServiceIntent) {
		updaterSettings.setUpdatePeriodInMinutes(frequencyNumberPicker.getUpdateInMinutes());
		updaterSettings.setRecipient(recipientTextField.getContent());
		updaterSettings.setDestination(destinationTextField.getContent());
		updaterSettings.setUpdatesActive(true);
		context.startService(updateServiceIntent);
	}

	private boolean validateForm() {

		boolean formValid = true;
		if (!recipientTextField.validate()) {
			formValid = false;
		};
		if (!destinationTextField.validate()) {
			formValid = false;
		}
		return formValid;
	}

	private class AsynchronousFormValidator extends AsyncTask<Void, Void, Boolean> {

		private CompoundButton buttonView;
		private Intent intent;

		public AsynchronousFormValidator(CompoundButton buttonView, Intent intent) {
			this.buttonView = buttonView;
			this.intent = intent;
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			return validateForm();
		}

		@Override
		protected void onPostExecute(Boolean validatedSuccessfully) {
			if (validatedSuccessfully) {
				StartSwitchListener.this.onFormValidateSuccess(intent);
			}
			else {
				StartSwitchListener.this.onFormValidateFailure(buttonView);
			}
			updateButtonStates();
		}
	}
}
