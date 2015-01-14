package com.matthewsimonmould.proudmary_v2;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Switch;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		SharedPreferences updatesActiveSharedPreferences = getApplicationContext().getSharedPreferences(StoredUpdateSetting.UPDATES_ACTIVE_PREFERENCES, 0);
		StoredUpdateSetting storedUpdateSetting = new StoredUpdateSetting(updatesActiveSharedPreferences);

        Switch switchButton = (Switch)findViewById(R.id.start_switch);
        switchButton.setChecked(storedUpdateSetting.isUpdatesActive());

		StartSwitchListener startSwitchListener = new StartSwitchListener(getApplicationContext(), storedUpdateSetting);
		switchButton.setOnCheckedChangeListener(startSwitchListener);
    }
}
