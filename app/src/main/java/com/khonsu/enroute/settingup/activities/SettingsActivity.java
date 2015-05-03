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

		CheckBox includeMapsLinkCheckBox = (CheckBox)findViewById(R.id.includeMapsLinkCheckBox);
		includeMapsLinkCheckBox.setChecked(updaterSettings.isIncludeMapsLink());
		includeMapsLinkCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updaterSettings.setIncludeMapsLink(isChecked);
			}
		});
	}


}
