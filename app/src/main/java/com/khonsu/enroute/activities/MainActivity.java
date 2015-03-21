package com.khonsu.enroute.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.khonsu.enroute.AddressValidator;
import com.khonsu.enroute.GooglePlacesAutocompleter;
import com.khonsu.enroute.NetworkStatus;
import com.khonsu.enroute.NextUpdateFormatter;
import com.khonsu.enroute.PhoneNumberValidator;
import com.khonsu.enroute.PlacesAutoCompleteAdapter;
import com.khonsu.enroute.R;
import com.khonsu.enroute.UpdateScheduler;
import com.khonsu.enroute.UpdaterService;
import com.khonsu.enroute.UrlAccessor;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.FrequencyNumberPicker;
import com.khonsu.enroute.uifields.TextField;
import com.khonsu.enroute.validator.FormValidator;

public class MainActivity extends Activity {
	static final int PICK_CONTACT_REQUEST = 1001;
	private TextField recipientTextField;
	private BroadcastReceiver nextMessageReceiver;
    private TextField destinationTextField;
    private FrequencyNumberPicker frequencyNumberPicker;
    private ImageButton contactPickerButton;
	private UpdaterSettings updaterSettings;
    private ToggleButton toggleButton;
    private RadioGroup modeOfTravelRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

		updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));

		frequencyNumberPicker = initialiseFrequencyNumberPicker();

		initialiseRecipientTextField();

		contactPickerButton = (ImageButton)findViewById(R.id.button_contact_picker);
		setUpContactPickerButton(contactPickerButton);

		destinationTextField = initialiseDestinationTextField();

		initialiseToggleButton();

		updateNextUpdateView();

		initialiseNextMessageUpdateReceiver();

        modeOfTravelRadioGroup = (RadioGroup)findViewById(R.id.mode_of_travel_radio_group);
        modeOfTravelRadioGroup.check(updaterSettings.getTransportMode());
	}

	private ImageButton setUpContactPickerButton(ImageButton contactPickerButton) {
		contactPickerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT_REQUEST);
			}
		});
		contactPickerButton.setEnabled(!updaterSettings.isUpdatesActive());
		contactPickerButton.setImageAlpha(updaterSettings.isUpdatesActive() ? 100: 255);
		return contactPickerButton;
	}

	private void initialiseNextMessageUpdateReceiver() {
		nextMessageReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				updateNextUpdateView();
			}
		};
		registerReceiver(nextMessageReceiver, new IntentFilter(UpdateScheduler.SCHEDULE_CHANGE));
	}

	private TextField initialiseDestinationTextField() {
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.destination);
		GooglePlacesAutocompleter googlePlacesAutocompleter = new GooglePlacesAutocompleter(new UrlAccessor());
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.list_item, googlePlacesAutocompleter));

		TextField destinationTextField = new TextField(autoCompView, updaterSettings, new AddressValidator(googlePlacesAutocompleter), "Invalid address");
		destinationTextField.setTextField(updaterSettings.getDestination());
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
		return destinationTextField;
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

	private void initialiseRecipientTextField() {
		recipientTextField = new TextField((EditText)findViewById(R.id.recipientNumber), updaterSettings, new PhoneNumberValidator(), "Invalid number");
		recipientTextField.setTextField(updaterSettings.getRecipient());
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
	}

	private FrequencyNumberPicker initialiseFrequencyNumberPicker() {
		FrequencyNumberPicker frequencyNumberPicker = new FrequencyNumberPicker((android.widget.NumberPicker) findViewById(R.id.numberPicker), updaterSettings);
		frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
		frequencyNumberPicker.setSelectedFrequency(updaterSettings.getUpdatePeriodInMinutes());
		return frequencyNumberPicker;
	}

	private void initialiseToggleButton() {
        StartSwitchListener startSwitchListener = new StartSwitchListener(new NetworkStatus(getApplicationContext()));
		toggleButton = (ToggleButton)findViewById(R.id.start_toggle);
		toggleButton.setChecked(updaterSettings.isUpdatesActive());
		toggleButton.setOnCheckedChangeListener(startSwitchListener);
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

    private void updateButtonStates() {
        frequencyNumberPicker.setEnabledOrDisabledAccordingToUpdateStatus();
        recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();
        destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();
        contactPickerButton.setEnabled(!updaterSettings.isUpdatesActive());
        contactPickerButton.setImageAlpha(updaterSettings.isUpdatesActive() ? 100: 255);
        toggleButton.setChecked(updaterSettings.isUpdatesActive());

    }

    private class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

        private final NetworkStatus networkStatus;

        public StartSwitchListener(NetworkStatus networkStatus) {
            this.networkStatus = networkStatus;
        }
        @Override
        public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
            if (isChecked && networkStatus.isAvailable()) {
                FormValidator formValidator = new FormValidator(new FormValidator.FormValidatorListener() {
                    @Override
                    public void success() {
                        updaterSettings.setUpdatePeriodInMinutes(frequencyNumberPicker.getUpdateInMinutes());
                        updaterSettings.setRecipient(recipientTextField.getContent());
                        updaterSettings.setDestination(destinationTextField.getContent());
                        updaterSettings.setTransportMode(modeOfTravelRadioGroup.getCheckedRadioButtonId());
                        updaterSettings.setUpdatesActive(true);


                        Intent updateServiceIntent = new Intent(getApplicationContext(), UpdaterService.class);
                        getApplicationContext().startService(updateServiceIntent);
                        updateButtonStates();
                    }

                    @Override
                    public void failure() {
                        buttonView.setChecked(false);
                    }
                });
                formValidator.execute(recipientTextField, destinationTextField);
            }
            else {
                updaterSettings.setUpdatesActive(false);
                UpdateScheduler.cancelUpdate(getApplicationContext());
                updateButtonStates();
            }
        }
    }
}
