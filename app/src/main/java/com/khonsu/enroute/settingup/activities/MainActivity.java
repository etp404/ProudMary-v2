package com.khonsu.enroute.settingup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.khonsu.enroute.AddressValidator;
import com.khonsu.enroute.GooglePlacesAutocompleter;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.settingup.MainPresenter;
import com.khonsu.enroute.settingup.MainView;
import com.khonsu.enroute.usernotifications.Notifier;
import com.khonsu.enroute.util.Navigator;
import com.khonsu.enroute.util.NetworkStatus;
import com.khonsu.enroute.R;
import com.khonsu.enroute.usernotifications.Toaster;
import com.khonsu.enroute.util.UiThreadExecutor;
import com.khonsu.enroute.UrlAccessor;
import com.khonsu.enroute.settingup.contacts.ContactSuggester;
import com.khonsu.enroute.settingup.contacts.ContactsAccessor;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.settingup.contacts.ContactsAccessorListener;
import com.khonsu.enroute.settingup.uifields.FrequencyPicker;
import com.khonsu.enroute.settingup.validator.FormValidator;

public class MainActivity extends Activity {

	private MainView mainView;
	private ContactsAccessor contactsAccessor;
	private GooglePlacesAutocompleter googlePlacesAutocompleter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		contactsAccessor = new ContactsAccessor(getContentResolver());
		googlePlacesAutocompleter = new GooglePlacesAutocompleter(new UrlAccessor());

		ImageButton contactPickerButton = (ImageButton) findViewById(R.id.button_contact_picker);
		contactPickerButton.setOnClickListener(new ContactPickerButtonListener());

		AutoCompleteTextView recipientTextView = initaliseRecipientField();
		AutoCompleteTextView destinationTextView = initaliseDestinationField();

		ToggleButton startToggleButton = (ToggleButton) findViewById(R.id.start_toggle);
		mainView = new MainView(recipientTextView,
								(ProgressBar) findViewById(R.id.recipientsLoadingSpinner),
								contactPickerButton,
								destinationTextView,
								(RadioGroup)findViewById(R.id.mode_of_travel_radio_group),
								new FrequencyPicker((NumberPicker) findViewById(R.id.numberPicker)),
								startToggleButton);

		MainPresenter mainPresenter = new MainPresenter(
				new NetworkStatus(getApplicationContext()),
				Notifier.getInstance(getApplicationContext()),
				new Toaster(getApplicationContext()),
				new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0)),
				new Navigator(getApplicationContext()), mainView,
				new FormValidator(new UiThreadExecutor(), new AddressValidator(googlePlacesAutocompleter)),
				new UpdateScheduler(getApplicationContext()));

		mainPresenter.populateView();

		startToggleButton.setOnCheckedChangeListener(new StartSwitchListener(mainPresenter));
		contactsAccessor.setListener(new ContactsAccessorListener(mainView));
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
		else if (id == R.id.settings) {
			startActivity(new Intent(this, SettingsActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

	private AutoCompleteTextView initaliseRecipientField() {
		AutoCompleteTextView recipientTextView = (AutoCompleteTextView) findViewById(R.id.recipientNumber);
		recipientTextView.setAdapter(
				new AutoCompleteAdapter(this, R.layout.list_item, new ContactSuggester(contactsAccessor)));
		return recipientTextView;
	}

	private AutoCompleteTextView initaliseDestinationField() {
		AutoCompleteTextView destinationTextView = (AutoCompleteTextView) findViewById(R.id.destination);
		destinationTextView.setAdapter(
				new AutoCompleteAdapter(this, R.layout.list_item, googlePlacesAutocompleter));
		return destinationTextView;
	}

	private class ContactPickerButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
			startActivityForResult(intent, R.id.contacts_result);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode,
									Intent data) {
		if (requestCode == R.id.contacts_result && resultCode == RESULT_OK) {
			mainView.setRecipient(contactsAccessor.getContact(data.getData()).toString());
		}
	}

}
