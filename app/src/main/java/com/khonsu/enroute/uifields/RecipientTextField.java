package com.khonsu.enroute.uifields;

import android.widget.EditText;

import com.khonsu.enroute.settings.UpdaterSettings;

public class RecipientTextField {
	private final EditText editableField;
	private final UpdaterSettings updaterSettings;

	public RecipientTextField(EditText editableField, UpdaterSettings updaterSettings) {
		this.editableField = editableField;
		this.updaterSettings = updaterSettings;
	}

	public String getRecipientNumber() {
		return editableField.getText().toString();
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		editableField.setEnabled(!updaterSettings.isUpdatesActive());
	}

	public void setTextField(String recipient) {
		editableField.setText(recipient);

	}

	public void highlightError() {
		editableField.setError("Invalid number"); //TODO: extract this.
	}
}