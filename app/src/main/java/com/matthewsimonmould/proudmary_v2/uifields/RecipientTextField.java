package com.matthewsimonmould.proudmary_v2.uifields;

import android.widget.EditText;

import com.matthewsimonmould.proudmary_v2.settings.StoredUpdateSetting;

public class RecipientTextField {
	private final EditText editableField;
	private final StoredUpdateSetting storedUpdateSetting;

	public RecipientTextField(EditText editableField, StoredUpdateSetting storedUpdateSetting) {
		this.editableField = editableField;
		this.storedUpdateSetting = storedUpdateSetting;
	}

	public String getRecipientNumber() {
		return editableField.getText().toString();
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		editableField.setEnabled(!storedUpdateSetting.isUpdatesActive());
	}

	public void setTextField(String recipient) {
		editableField.setText(recipient);

	}
}
