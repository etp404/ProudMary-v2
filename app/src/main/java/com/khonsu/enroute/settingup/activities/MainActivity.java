package com.khonsu.enroute.settingup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.khonsu.enroute.AddressValidator;
import com.khonsu.enroute.GooglePlacesAutocompleter;
import com.khonsu.enroute.events.EventBus;
import com.khonsu.enroute.settingup.BroadcastSender;
import com.khonsu.enroute.settingup.MainPresenter;
import com.khonsu.enroute.settingup.MainView;
import com.khonsu.enroute.settingup.TurnOffUpdatesReceiver;
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

	private final UpdatesOffConsumer updatesOffConsumer = new UpdatesOffConsumer();
	private MainView mainView;
	private ContactsAccessor contactsAccessor;
	private GooglePlacesAutocompleter googlePlacesAutocompleter;
	private MainPresenter mainPresenter;

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

		Button startButton = (Button) findViewById(R.id.start_button);
		Button stopButton = (Button) findViewById(R.id.stop_button);
		mainView = new MainView(recipientTextView,
								(ProgressBar) findViewById(R.id.recipientsLoadingSpinner),
								contactPickerButton,
								destinationTextView,
								(RadioGroup)findViewById(R.id.mode_of_travel_radio_group),
								new FrequencyPicker((NumberPicker) findViewById(R.id.numberPicker)),
								startButton,
								stopButton);

		mainPresenter = new MainPresenter(
				new BroadcastSender(getApplicationContext()),
				new NetworkStatus(getApplicationContext()),
				new Toaster(getApplicationContext()),
				new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0)),
				mainView,
				new FormValidator(new UiThreadExecutor(), new AddressValidator(googlePlacesAutocompleter)));

		mainPresenter.populateView();

		startButton.setOnClickListener(new StartButtonListener(mainPresenter));
		stopButton.setOnClickListener(new StopButtonListener(mainPresenter));
		contactsAccessor.setListener(new ContactsAccessorListener(mainView));

		EventBus.getInstance().register(TurnOffUpdatesReceiver.UPDATES_TURNED_OFF, updatesOffConsumer);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getInstance().unregister(TurnOffUpdatesReceiver.UPDATES_TURNED_OFF, updatesOffConsumer);
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

	private class UpdatesOffConsumer implements EventBus.Consumer {
		@Override
		public void invoke(Object payload) {
			mainPresenter.sendingStopped();
		}
	}

}
