package com.khonsu.enroute.uifields;

import android.widget.EditText;

import com.khonsu.enroute.FieldValidator;
import com.khonsu.enroute.settings.UpdaterSettings;

public class TextField {
	private final EditText editableField;
	private final UpdaterSettings updaterSettings;
	private final FieldValidator validator;
	private final String errorMessage;
	private boolean isInErrorState = false;

	public TextField(EditText editableField, UpdaterSettings updaterSettings, FieldValidator validator, String errorMessage) {
		this.editableField = editableField;
		this.updaterSettings = updaterSettings;
		this.validator = validator;
		this.errorMessage = errorMessage;
	}

	public void setTextField(String textFieldContent) {
		editableField.setText(textFieldContent);
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		editableField.setEnabled(!updaterSettings.isUpdatesActive());
		if (isInErrorState) {
			highlightError();
		}
	}

	public String getContent() {
		return editableField.getText().toString();
	}

	public void highlightError() {
		editableField.setError(errorMessage);
	}

	public boolean validate() {
		isInErrorState = false;
		if (!validator.validate(getContent())) {
			isInErrorState = true;
			return false;
		}
		return true;
	}
}