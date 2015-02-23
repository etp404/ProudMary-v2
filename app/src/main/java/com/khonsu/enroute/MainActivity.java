package com.khonsu.enroute;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.DestinationTextField;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.RecipientTextField;

public class MainActivity extends ActionBarActivity {
	static final int PICK_CONTACT_REQUEST = 1001;
	private RecipientTextField recipientTextField;
	private BroadcastReceiver nextMessageReceiver;
	private UpdaterSettings updaterSettings;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));

		ToggleButton toggleButton = (ToggleButton)findViewById(R.id.start_toggle);
		toggleButton.setChecked(updaterSettings.isUpdatesActive());

		FrequencyNumberPicker frequencyNumberPicker = createFrequencyNumberPicker(updaterSettings);
		setUpRecipientTextField(updaterSettings);

		ImageButton contactPickerButton = (ImageButton)findViewById(R.id.button_contact_picker);
		contactPickerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT_REQUEST);

			}
		});
		contactPickerButton.setEnabled(!updaterSettings.isUpdatesActive());
		contactPickerButton.setImageAlpha(updaterSettings.isUpdatesActive() ? 100: 255); //TODO: pull our contact picker into own class.

		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.destination);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item, new GooglePlacesAutocompleter(new UrlAccessor())));

		DestinationTextField destinationTextField = new DestinationTextField(autoCompView, updaterSettings);
		destinationTextField.setTextField(updaterSettings.getDestination());
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();

		StartSwitchListener startSwitchListener = new StartSwitchListener(getApplicationContext(), updaterSettings, destinationTextField, frequencyNumberPicker, recipientTextField, contactPickerButton);
		toggleButton.setOnCheckedChangeListener(startSwitchListener);

		updateNextUpdateView();

		nextMessageReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				updateNextUpdateView();
			}
		};
		registerReceiver(nextMessageReceiver, new IntentFilter(UpdateScheduler.SCHEDULE_CHANGE));
	}

	private void updateNextUpdateView() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				TextView nextUpdateView = (TextView) MainActivity.this.findViewById(R.id.next_update_view);
				if (updaterSettings.isUpdatesActive()) {
					nextUpdateView.setText(NextUpdateFormatter.format(updaterSettings.getTimeForNextUpdateInMillis()));
					nextUpdateView.setVisibility(View.VISIBLE);
				} else {
					nextUpdateView.setVisibility(View.GONE);
				}
			}
		};
		runnable.run();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(nextMessageReceiver);
	}

	private void setUpRecipientTextField(UpdaterSettings updaterSettings) {
		recipientTextField = new RecipientTextField((EditText)findViewById(R.id.recipientNumber), updaterSettings);
		recipientTextField.setTextField(updaterSettings.getRecipient());
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
	}

	private FrequencyNumberPicker createFrequencyNumberPicker(UpdaterSettings updaterSettings) {
		FrequencyNumberPicker frequencyNumberPicker = new FrequencyNumberPicker((android.widget.NumberPicker) findViewById(R.id.numberPicker), updaterSettings);
		frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
		frequencyNumberPicker.setSelectedFrequency(updaterSettings.getUpdatePeriodInMinutes());
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
