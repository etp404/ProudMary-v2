package com.khonsu.enroute;

import android.content.Context;
import android.text.Editable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.RecipientTextField;
import com.khonsu.enroute.uifields.TextField;

import org.junit.Test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

		PhoneNumberValidator phoneNumberValidator = mock(PhoneNumberValidator.class);
		when(phoneNumberValidator.isValidPhoneNumber(anyString())).thenReturn(false);

		RecipientTextField recipientTextField = new RecipientTextField(recipientEditText, mock(UpdaterSettings.class), phoneNumberValidator);

		StartSwitchListener startSwitchListener = new StartSwitchListener(
				mock(Context.class),
				mock(UpdaterSettings.class),
				mock(TextField.class),
				mock(FrequencyNumberPicker.class),
				recipientTextField,
				mock(ImageButton.class));

		CompoundButton compoundButton = mock(CompoundButton.class);
		startSwitchListener.onCheckedChanged(compoundButton, true);

		verify(compoundButton, times(1)).setChecked(false);
		verify(recipientEditText, times(1)).setError(anyString());
	}

	@Test
	public void testThatIfLocationIncorrectFormIsRejected() {
		Editable mockEditable = mock(Editable.class);
		when(mockEditable.toString()).thenReturn("");

		EditText mockRecipientEditText = mock(EditText.class);
		when(mockRecipientEditText.getText()).thenReturn(mockEditable);

		EditText mockDestinationEditText = mock(EditText.class);
		when(mockDestinationEditText.getText()).thenReturn(mockEditable);

		PhoneNumberValidator phoneNumberValidator = mock(PhoneNumberValidator.class);
		when(phoneNumberValidator.isValidPhoneNumber(anyString())).thenReturn(true);

		AddressValidator addressValidator = mock(AddressValidator.class);
		when(addressValidator.validate(anyString())).thenReturn(false);

		RecipientTextField recipientTextField = new RecipientTextField(mockRecipientEditText, mock(UpdaterSettings.class), phoneNumberValidator);
		TextField destinationTextField = new TextField(mockDestinationEditText, mock(UpdaterSettings.class), addressValidator, "some message");

		StartSwitchListener startSwitchListener = new StartSwitchListener(
				mock(Context.class),
				mock(UpdaterSettings.class),
				destinationTextField,
				mock(FrequencyNumberPicker.class),
				recipientTextField,
				mock(ImageButton.class));

		CompoundButton compoundButton = mock(CompoundButton.class);
		startSwitchListener.onCheckedChanged(compoundButton, true);

		verify(compoundButton, times(1)).setChecked(false);
		verify(mockRecipientEditText, never()).setError(anyString());
		verify(mockDestinationEditText, times(1)).setError(anyString());
	}

	@Test
	public void testThatIfLocationCorrectFormIsNotRejected() {
		Editable mockEditable = mock(Editable.class);
		when(mockEditable.toString()).thenReturn("");

		EditText mockEditText = mock(EditText.class);
		when(mockEditText.getText()).thenReturn(mockEditable);

		PhoneNumberValidator phoneNumberValidator = mock(PhoneNumberValidator.class);
		when(phoneNumberValidator.isValidPhoneNumber(anyString())).thenReturn(true);

		AddressValidator addressValidator = mock(AddressValidator.class);
		when(addressValidator.validate(anyString())).thenReturn(true);

		RecipientTextField recipientTextField = new RecipientTextField(mockEditText, mock(UpdaterSettings.class), phoneNumberValidator);
		TextField destinationTextField = new TextField(mockEditText, mock(UpdaterSettings.class), addressValidator, "some message");

		StartSwitchListener startSwitchListener = new StartSwitchListener(
				mock(Context.class),
				mock(UpdaterSettings.class),
				destinationTextField,
				mock(FrequencyNumberPicker.class),
				recipientTextField,
				mock(ImageButton.class));

		CompoundButton compoundButton = mock(CompoundButton.class);
		startSwitchListener.onCheckedChanged(compoundButton, true);

		verify(compoundButton, never()).setChecked(false);
	}

	@Test
	public void testThatIfLocationAnRecipientAreBothIncorrectFormIsRejectedAndErrorsAreHighlighted() {
		Editable mockEditable = mock(Editable.class);
		when(mockEditable.toString()).thenReturn("");

		EditText mockRecipientEditText = mock(EditText.class);
		when(mockRecipientEditText.getText()).thenReturn(mockEditable);

		EditText mockDestinationEditText = mock(EditText.class);
		when(mockDestinationEditText.getText()).thenReturn(mockEditable);

		PhoneNumberValidator phoneNumberValidator = mock(PhoneNumberValidator.class);
		when(phoneNumberValidator.isValidPhoneNumber(anyString())).thenReturn(false);

		AddressValidator addressValidator = mock(AddressValidator.class);
		when(addressValidator.validate(anyString())).thenReturn(false);

		RecipientTextField recipientTextField = new RecipientTextField(mockRecipientEditText, mock(UpdaterSettings.class), phoneNumberValidator);
		TextField destinationTextField = new TextField(mockDestinationEditText, mock(UpdaterSettings.class), addressValidator, "some message");

		StartSwitchListener startSwitchListener = new StartSwitchListener(
				mock(Context.class),
				mock(UpdaterSettings.class),
				destinationTextField,
				mock(FrequencyNumberPicker.class),
				recipientTextField,
				mock(ImageButton.class));

		CompoundButton compoundButton = mock(CompoundButton.class);
		startSwitchListener.onCheckedChanged(compoundButton, true);

		verify(compoundButton, times(1)).setChecked(false);
		verify(mockRecipientEditText, times(1)).setError(anyString());
		verify(mockDestinationEditText, times(1)).setError(anyString());
	}
}
