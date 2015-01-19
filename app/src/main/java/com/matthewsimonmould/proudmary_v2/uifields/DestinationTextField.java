package com.matthewsimonmould.proudmary_v2.uifields;

import android.widget.EditText;

import com.matthewsimonmould.proudmary_v2.StoredUpdateSetting;

public class DestinationTextField {
	private final EditText editableField;
	private final StoredUpdateSetting storedUpdateSetting;

	public DestinationTextField(EditText editableField, StoredUpdateSetting storedUpdateSetting) {
		this.editableField = editableField;
		this.storedUpdateSetting = storedUpdateSetting;
	}

	public void setTextField(String destination) {
		editableField.setText(destination);
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		editableField.setEnabled(!storedUpdateSetting.isUpdatesActive());
	}

	public String getDestination() {
		return editableField.getText().toString();
	}
}
