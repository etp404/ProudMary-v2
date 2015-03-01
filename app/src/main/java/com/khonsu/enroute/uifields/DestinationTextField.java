package com.khonsu.enroute.uifields;

import android.widget.EditText;

import com.khonsu.enroute.AddressValidator;
import com.khonsu.enroute.settings.UpdaterSettings;

public class DestinationTextField {
	private final EditText editableField;
	private final UpdaterSettings updaterSettings;
	private final AddressValidator addressValidator;

	public DestinationTextField(EditText editableField, UpdaterSettings updaterSettings, AddressValidator addressValidator) {
		this.editableField = editableField;
		this.updaterSettings = updaterSettings;
		this.addressValidator = addressValidator;
	}

	public void setTextField(String destination) {
		editableField.setText(destination);
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		editableField.setEnabled(!updaterSettings.isUpdatesActive());
	}

	public String getDestination() {
		return editableField.getText().toString();
	}

	public void highlightError() {
		editableField.setError("Invalid address");
	}

	public boolean validate() {
		if (!addressValidator.validate(getDestination())) {
			highlightError();
			return false;
		}
		return true;
	}
}
