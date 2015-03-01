package com.khonsu.enroute;

import android.content.Context;
import android.text.Editable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.DestinationTextField;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.RecipientTextField;

import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartSwitchListenerTest {
	@Test
	public void testThatIfNumberIncorrectFormIsRejected() {
		Editable recipientEditable = mock(Editable.class);
		when(recipientEditable.toString()).thenReturn("");

		EditText recipientEditText = mock(EditText.class);
		when(recipientEditText.getText()).thenReturn(recipientEditable);

		PhoneNumberValidator phoneNumberValidator = mock (PhoneNumberValidator.class);
		when(phoneNumberValidator.isValidPhoneNumber(anyString())).thenReturn(false);

		RecipientTextField recipientTextField = new RecipientTextField(recipientEditText, mock(UpdaterSettings.class), phoneNumberValidator);

		StartSwitchListener startSwitchListener = new StartSwitchListener(
				mock(Context.class),
				mock(UpdaterSettings.class),
				mock(DestinationTextField.class),
				mock(FrequencyNumberPicker.class),
				recipientTextField,
				mock(ImageButton.class));

		CompoundButton compoundButton = mock(CompoundButton.class);
		startSwitchListener.onCheckedChanged(compoundButton, true);

		verify(compoundButton, times(1)).setChecked(false);
	}

	@Test
	public void testThatIfNumberCorrectFormIsNotRejected() {

		PhoneNumberValidator phoneNumberValidator = mock (PhoneNumberValidator.class);
		when(phoneNumberValidator.isValidPhoneNumber(anyString())).thenReturn(true);

		Editable recipientEditable = mock(Editable.class);
		when(recipientEditable.toString()).thenReturn("");

		EditText recipientEditText = mock(EditText.class);
		when(recipientEditText.getText()).thenReturn(recipientEditable);

		RecipientTextField recipientTextField = new RecipientTextField(recipientEditText, mock(UpdaterSettings.class), phoneNumberValidator);

		StartSwitchListener startSwitchListener = new StartSwitchListener(
				mock(Context.class),
				mock(UpdaterSettings.class),
				mock(DestinationTextField.class),
				mock(FrequencyNumberPicker.class),
				recipientTextField,
				mock(ImageButton.class));

		CompoundButton compoundButton = mock(CompoundButton.class);
		startSwitchListener.onCheckedChanged(compoundButton, true);

		verify(compoundButton, times(0));
	}

}
