package com.khonsu.enroute.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.khonsu.enroute.AddressValidator;
import com.khonsu.enroute.GooglePlacesAutocompleter;
import com.khonsu.enroute.NetworkStatusProvider;
import com.khonsu.enroute.NextUpdateFormatter;
import com.khonsu.enroute.PhoneNumberValidator;
import com.khonsu.enroute.PlacesAutoCompleteAdapter;
import com.khonsu.enroute.R;
import com.khonsu.enroute.StartSwitchListener;
import com.khonsu.enroute.UpdateScheduler;
import com.khonsu.enroute.UrlAccessor;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.DestinationTextField;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.TextFieldWrapper;

public class MainActivity extends Activity {
	static final int PICK_CONTACT_REQUEST = 1001;
	private TextFieldWrapper recipientTextField;
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
		GooglePlacesAutocompleter googlePlacesAutocompleter = new GooglePlacesAutocompleter(new UrlAccessor());
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item, googlePlacesAutocompleter));

		DestinationTextField destinationTextField = new DestinationTextField(autoCompView, updaterSettings, new AddressValidator(googlePlacesAutocompleter));
		destinationTextField.setTextField(updaterSettings.getDestination());
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();

		NetworkStatusProvider networkStatusProvider = new NetworkStatusProvider((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
		StartSwitchListener startSwitchListener = new StartSwitchListener(getApplicationContext(), updaterSettings, destinationTextField, frequencyNumberPicker, recipientTextField, contactPickerButton, networkStatusProvider);
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



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.terms_and_conditions) {
			startActivity(new Intent(this, TermsAndConditionsActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

	private void setUpRecipientTextField(UpdaterSettings updaterSettings) {
		recipientTextField = new TextFieldWrapper((EditText)findViewById(R.id.recipientNumber), updaterSettings, new PhoneNumberValidator(), getResources().getString(R.string.recipient_error_message));
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
