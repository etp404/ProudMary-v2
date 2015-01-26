package com.khonsu.ontheway.uifields;

import android.widget.EditText;

import com.khonsu.ontheway.settings.UpdaterSettings;

public class DestinationTextField {
	private final EditText editableField;
	private final UpdaterSettings updaterSettings;

	public DestinationTextField(EditText editableField, UpdaterSettings updaterSettings) {
		this.editableField = editableField;
		this.updaterSettings = updaterSettings;
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
}
