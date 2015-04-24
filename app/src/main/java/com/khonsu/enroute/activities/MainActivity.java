package com.khonsu.enroute.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.khonsu.enroute.AutoCompleteAdapter;
import com.khonsu.enroute.MainPresenter;
import com.khonsu.enroute.MainView;
import com.khonsu.enroute.R;
import com.khonsu.enroute.contactsautocomplete.ContactSuggester;
import com.khonsu.enroute.contactsautocomplete.ContactsAccessor;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.uifields.FrequencyPicker;

public class MainActivity extends Activity {

	private MainView mainView;
	private ContactsAccessor contactsAccessor;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		contactsAccessor = new ContactsAccessor(getContentResolver());

		ImageButton contactPickerButton = (ImageButton) findViewById(R.id.button_contact_picker);
		contactPickerButton.setOnClickListener(new ContactPickerButtonListener());

		AutoCompleteTextView recipientTextView = (AutoCompleteTextView) findViewById(R.id.recipientNumber);

		recipientTextView.setAdapter(
				new AutoCompleteAdapter(this, R.layout.list_item, new ContactSuggester(contactsAccessor)));

		ToggleButton startToggleButton = (ToggleButton) findViewById(R.id.start_toggle);
		mainView = new MainView(recipientTextView,
								(ProgressBar) findViewById(R.id.recipientsLoadingSpinner),
								contactPickerButton,
								(EditText) findViewById(R.id.destination),
								(RadioGroup)findViewById(R.id.mode_of_travel_radio_group),
								new FrequencyPicker((NumberPicker) findViewById(R.id.numberPicker)),
								startToggleButton);

		MainPresenter mainPresenter = new MainPresenter(new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0)), mainView);

		mainPresenter.populateView();

		startToggleButton.setOnCheckedChangeListener(new StartSwitchListener(mainPresenter));
		contactsAccessor.setListener(new ContactsAccessorListener(mainView));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
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
