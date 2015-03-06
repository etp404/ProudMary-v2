package com.khonsu.enroute.uifields;

import android.widget.EditText;

import com.khonsu.enroute.PhoneNumberValidator;
import com.khonsu.enroute.settings.UpdaterSettings;

public class RecipientTextField {
	private final EditText editableField;
	private final UpdaterSettings updaterSettings;
	private PhoneNumberValidator phoneNumberValidator;
	boolean isInErrorState = false;

	public RecipientTextField(EditText editableField, UpdaterSettings updaterSettings, PhoneNumberValidator phoneNumberValidator) {
		this.editableField = editableField;
		this.updaterSettings = updaterSettings;
		this.phoneNumberValidator = phoneNumberValidator;
	}

	public String getRecipientNumber() {
		return editableField.getText().toString();
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		editableField.setEnabled(!updaterSettings.isUpdatesActive());
		if (isInErrorState) {
			highlightError();
		}
	}

	public void setTextField(String recipient) {
		editableField.setText(recipient);
	}

	public void highlightError() {
		editableField.setError("Invalid number"); //TODO: extract this.
	}

	public boolean validate() {
		isInErrorState = false;
		if (!phoneNumberValidator.isValidPhoneNumber(editableField.getText().toString())) {
			isInErrorState = true;
			return false;
		}
		return true;
	}
}
