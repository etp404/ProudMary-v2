package com.matthewsimonmould.proudmary_v2.uifields;

import android.widget.EditText;

import com.matthewsimonmould.proudmary_v2.settings.StoredUpdateSetting;

public class FrequencyTextField {
	private final EditText editableField;
	private final StoredUpdateSetting storedUpdateSetting;

	public FrequencyTextField(EditText editableField, StoredUpdateSetting storedUpdateSetting) {
		this.editableField = editableField;
		this.storedUpdateSetting = storedUpdateSetting;
	}

	public Integer getUpdateInMinutes() {
		return Integer.valueOf(editableField.getText().toString());
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		editableField.setEnabled(!storedUpdateSetting.isUpdatesActive());
	}

	public void setTextField(String updatePeriodInMinutes) {
		editableField.setText(updatePeriodInMinutes);
	}
}
