package com.khonsu.enroute.settingup;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.settingup.uifields.FrequencyPicker;

public class MainView {
	private static final String RECIPIENT_ERROR_MESSAGE = "Invalid number";
	private static final String DESTINATION_ERROR_MESSAGE = "Invalid address";

	private static int IMAGE_ALPHA_INACTIVE = 100;
	private static int IMAGE_ALPHA_ACTIVE = 255;

	private AutoCompleteTextView recipientTextView;
	private ProgressBar contactsLoadingSpinner;
	private ImageButton contactPickerButton;
	private EditText destinationView;
	private RadioGroup modeOfTravel;
	private FrequencyPicker frequencyPicker;
	private ToggleButton onOffButton;

	public MainView(AutoCompleteTextView recipientTextView,
					ProgressBar contactsLoadingSpinner,
					ImageButton contactPickerButton,
					EditText destinationView,
					RadioGroup modeOfTravel,
					FrequencyPicker frequencyPicker,
					ToggleButton onOffButton) {
		this.recipientTextView = recipientTextView;
		this.contactsLoadingSpinner = contactsLoadingSpinner;
		this.contactPickerButton = contactPickerButton;
		this.destinationView = destinationView;
		this.modeOfTravel = modeOfTravel;
		this.frequencyPicker = frequencyPicker;
		this.onOffButton = onOffButton;
	}

	public void showLoadingSpinner() {
		contactsLoadingSpinner.setVisibility(View.VISIBLE);
	}

	public void hideLoadingSpinner() {
		contactsLoadingSpinner.setVisibility(View.GONE);
	}

	public void makeFormActive(boolean active) {
		recipientTextView.setEnabled(active);
		makeContactPickerButtonActive(active);
		destinationView.setEnabled(active);
		makeModeOfTransportActive(active);
		frequencyPicker.setEnabled(active);
	}

	private void makeModeOfTransportActive(boolean active) {
		for (int i=0;i< modeOfTravel.getChildCount();i++) {
			modeOfTravel.getChildAt(i).setEnabled(active);
		}
	}

	private void makeContactPickerButtonActive(boolean active) {
		contactPickerButton.setEnabled(active);
		contactPickerButton.setImageAlpha(active ? IMAGE_ALPHA_ACTIVE : IMAGE_ALPHA_INACTIVE);
	}

	public void setRecipient(String recipient) {
		recipientTextView.setText(recipient);
	}

	public String getRecipient() {
		return recipientTextView.getText().toString();
	}

	public void setDestination(String destination) {
		destinationView.setText(destination);
	}

	public String getDestination() {
		return destinationView.getText().toString();
	}

	public void setUpdatePeriodInMinutes(int updatePeriodInMillis) {
		frequencyPicker.setUpdatePeriodInMinute(updatePeriodInMillis);
	}

	public int getUpdatePeriodInMinutes() {
		return frequencyPicker.getUpdateInMinutes();
	}

	public void setTransportMode(ModeOfTransport transportMode) {
		for (int i=0;i< modeOfTravel.getChildCount();i++) {
			RadioButton radioButton = (RadioButton) modeOfTravel.getChildAt(i);
			if (radioButton.getText().toString().toLowerCase().equals(transportMode.toString().toLowerCase())) {
				radioButton.setChecked(true);
				break;
			}
		}
	}

	public String getModeOfTravel() {
		RadioButton checkedTransportButton = (RadioButton) modeOfTravel.findViewById(modeOfTravel.getCheckedRadioButtonId());
		return checkedTransportButton.getText().toString();
	}

	public void setUpdatesActive(boolean updatesActive) {
		onOffButton.setChecked(updatesActive);
	}

	public boolean isUpdatesActive() {
		return onOffButton.isActivated();
	}

	public void showErrorOnRecipient() {
		recipientTextView.setError(RECIPIENT_ERROR_MESSAGE);
	}

	public void showErrorOnDestination() {
		destinationView.setError(DESTINATION_ERROR_MESSAGE);
	}
}
