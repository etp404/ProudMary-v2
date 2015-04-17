package com.khonsu.enroute;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class MainView {
	private static int IMAGE_ALPHA_INACTIVE = 100;
	private static int IMAGE_ALPHA_ACTIVE = 255;

	private EditText recipientTextView;
	private ProgressBar contactsLoadingSpinner;
	private ImageButton contactPickerButton;
	private EditText destinationView;
	private RadioGroup modeOfTransport;
	private NumberPicker frequencyPicker;
	private ToggleButton onOffButton;

	public MainView(EditText recipientTextView,
					ProgressBar contactsLoadingSpinner,
					ImageButton contactPickerButton,
					EditText destinationView,
					RadioGroup modeOfTransport,
					NumberPicker frequencyPicker,
					ToggleButton onOffButton) {
		this.recipientTextView = recipientTextView;
		this.contactsLoadingSpinner = contactsLoadingSpinner;
		this.contactPickerButton = contactPickerButton;
		this.destinationView = destinationView;
		this.modeOfTransport = modeOfTransport;
		this.frequencyPicker = frequencyPicker;
		this.onOffButton = onOffButton;
	}

	public void setRecipient(String recipient) {
		recipientTextView.setText(recipient);
	}

	public void showLoadingSpinner() {
		contactsLoadingSpinner.setVisibility(View.VISIBLE);
	}

	public void hideLoadingSpinner() {
		contactsLoadingSpinner.setVisibility(View.GONE);
	}

	public void makeFormActive(boolean active) {
		recipientTextView.setEnabled(active);
		destinationView.setEnabled(active);
		modeOfTransport.setEnabled(active);
		makeContactPickerButtonActive(active);
		makeModeOfTransportActive(active);
		frequencyPicker.setEnabled(active);
	}

	private void makeModeOfTransportActive(boolean active) {
		for (int i=0;i<modeOfTransport.getChildCount();i++) {
			modeOfTransport.getChildAt(i).setEnabled(active);
		}
	}

	private void makeContactPickerButtonActive(boolean active) {
		contactPickerButton.setEnabled(active);
		contactPickerButton.setImageAlpha(active ? IMAGE_ALPHA_ACTIVE : IMAGE_ALPHA_INACTIVE);
	}
}
