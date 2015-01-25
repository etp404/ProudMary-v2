package com.matthewsimonmould.proudmary_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.matthewsimonmould.proudmary_v2.settings.StoredUpdateSetting;
import com.matthewsimonmould.proudmary_v2.settings.UpdaterSettings;
import com.matthewsimonmould.proudmary_v2.uifields.DestinationTextField;
import com.matthewsimonmould.proudmary_v2.uifields.FrequencyNumberPicker;
import com.matthewsimonmould.proudmary_v2.uifields.RecipientTextField;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
	static final int PICK_CONTACT_REQUEST = 1001;
	private RecipientTextField recipientTextField;
	final List<String> incrementValues = new ArrayList<>();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		SharedPreferences updatesActiveSharedPreferences = getApplicationContext().getSharedPreferences(StoredUpdateSetting.UPDATES_ACTIVE_PREFERENCES, 0);
		StoredUpdateSetting storedUpdateSetting = new StoredUpdateSetting(updatesActiveSharedPreferences);
		UpdaterSettings updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));

		ToggleButton toggleButton = (ToggleButton)findViewById(R.id.start_toggle);
		toggleButton.setChecked(storedUpdateSetting.isUpdatesActive());

		FrequencyNumberPicker frequencyNumberPicker = createFrequencyNumberPicker(storedUpdateSetting, updaterSettings);
		setUpRecipientTextField(storedUpdateSetting, updaterSettings);
		setUpContactPicker();

		DestinationTextField destinationTextField = new DestinationTextField((EditText)findViewById(R.id.destination), storedUpdateSetting);
		destinationTextField.setTextField(updaterSettings.getDestination());
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();

		StartSwitchListener startSwitchListener = new StartSwitchListener(getApplicationContext(), storedUpdateSetting, updaterSettings, destinationTextField, frequencyNumberPicker, recipientTextField);
		toggleButton.setOnCheckedChangeListener(startSwitchListener);
	}

	private void setUpContactPicker() {
		ImageButton contactPickerButton = (ImageButton)findViewById(R.id.button_contact_picker);
		contactPickerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT_REQUEST);

			}
		});
	}

	private void setUpRecipientTextField(StoredUpdateSetting storedUpdateSetting, UpdaterSettings updaterSettings) {
		recipientTextField = new RecipientTextField((EditText)findViewById(R.id.recipientNumber), storedUpdateSetting);
		recipientTextField.setTextField(updaterSettings.getRecipient());
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
	}

	private FrequencyNumberPicker createFrequencyNumberPicker(StoredUpdateSetting storedUpdateSetting, UpdaterSettings updaterSettings) {
		FrequencyNumberPicker frequencyNumberPicker = new FrequencyNumberPicker((android.widget.NumberPicker) findViewById(R.id.numberPicker), storedUpdateSetting);
		frequencyNumberPicker.setSelectedFrequency(updaterSettings.getUpdatePeriodInMinutes());
		frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
		return frequencyNumberPicker;
	}

	protected void onActivityResult(int requestCode, int resultCode,
									Intent data) {
		if (requestCode == PICK_CONTACT_REQUEST) {
			if (resultCode == RESULT_OK) {
				Uri uri = data.getData();
				Cursor cursor = getContentResolver().query(uri, null, null, null, null);
				cursor.moveToFirst();
				int  phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
				recipientTextField.setTextField(cursor.getString(phoneIndex));
			}
		}
	}
}
