package com.khonsu.enroute.settingup.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.khonsu.enroute.R;
import com.khonsu.enroute.settings.UpdaterSettings;

public final class SettingsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

		final UpdaterSettings updaterSettings = new UpdaterSettings(getApplicationContext().getSharedPreferences(UpdaterSettings.UPDATER_SETTINGS, 0));

		setUpMapsLinkButton(updaterSettings);
		setUpDistanceButton(updaterSettings);
	}

	private void setUpDistanceButton(final UpdaterSettings updaterSettings) {
		CheckBox includeDistanceCheckBox = (CheckBox)findViewById(R.id.includeDistanceInMessage);
		includeDistanceCheckBox.setChecked(updaterSettings.isIncludeMapsLink());

		includeDistanceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updaterSettings.setIncludeDistance(isChecked);
			}
		});
	}

	private void setUpMapsLinkButton(final UpdaterSettings updaterSettings) {
		CheckBox includeMapsLinkCheckBox = (CheckBox)findViewById(R.id.includeMapsLinkCheckBox);
		includeMapsLinkCheckBox.setChecked(updaterSettings.isIncludeDistanceInMessage());

		includeMapsLinkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updaterSettings.setIncludeMapsLink(isChecked);
			}
		});
	}


}
