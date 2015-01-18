package com.matthewsimonmould.proudmary_v2;

import android.widget.EditText;

import java.util.concurrent.TimeUnit;

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

	public long getUpdateInMillis() {
		return TimeUnit.MINUTES.toMillis(getUpdateInMinutes());
	}
}
