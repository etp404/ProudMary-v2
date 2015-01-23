package com.matthewsimonmould.proudmary_v2;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.matthewsimonmould.proudmary_v2.settings.StoredUpdateSetting;
import com.matthewsimonmould.proudmary_v2.settings.UpdaterSettings;
import com.matthewsimonmould.proudmary_v2.uifields.DestinationTextField;
import com.matthewsimonmould.proudmary_v2.uifields.FrequencyTextField;
import com.matthewsimonmould.proudmary_v2.uifields.RecipientTextField;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		SharedPreferences updatesActiveSharedPreferences = getApplicationContext().getSharedPreferences(StoredUpdateSetting.UPDATES_ACTIVE_PREFERENCES, 0);
		StoredUpdateSetting storedUpdateSetting = new StoredUpdateSetting(updatesActiveSharedPreferences);
		UpdaterSettings updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));

        Switch switchButton = (Switch)findViewById(R.id.start_switch);
		switchButton.setChecked(storedUpdateSetting.isUpdatesActive());

		FrequencyTextField frequencyTextField = new FrequencyTextField((EditText)findViewById(R.id.frequency), storedUpdateSetting);
		frequencyTextField.setTextField(updaterSettings.getUpdatePeriodInMinutes());
		frequencyTextField.setEnabledOrDisabledAccordingToUpdateStatus(); //TODO: set this to picker.

		RecipientTextField recipientTextField = new RecipientTextField((EditText)findViewById(R.id.recipientNumber), storedUpdateSetting);
		recipientTextField.setTextField(updaterSettings.getRecipient());
		recipientTextField.setEnabledOrDisabledAccordingToUpdateStatus();

		DestinationTextField destinationTextField = new DestinationTextField((EditText)findViewById(R.id.destination), storedUpdateSetting);
		destinationTextField.setTextField(updaterSettings.getDestination());
		destinationTextField.setEnabledOrDisabledAccordingToUpdateStatus();

		StartSwitchListener startSwitchListener = new StartSwitchListener(getApplicationContext(), storedUpdateSetting, updaterSettings, destinationTextField, frequencyTextField, recipientTextField);
		switchButton.setOnCheckedChangeListener(startSwitchListener);
    }
}
