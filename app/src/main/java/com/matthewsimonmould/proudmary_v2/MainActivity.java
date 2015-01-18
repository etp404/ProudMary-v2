package com.matthewsimonmould.proudmary_v2;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.matthewsimonmould.proudmary_v2.uifields.FrequencyTextField;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		SharedPreferences updatesActiveSharedPreferences = getApplicationContext().getSharedPreferences(StoredUpdateSetting.UPDATES_ACTIVE_PREFERENCES, 0);
		StoredUpdateSetting storedUpdateSetting = new StoredUpdateSetting(updatesActiveSharedPreferences);
		SharedPreferences updatePeriodSharedPreferences = getApplicationContext().getSharedPreferences(UpdatePeriodInMinutesSetting.UPDATES_PERIOD_PREFERENCES, 0);
		UpdatePeriodInMinutesSetting updatePeriodSetting = new UpdatePeriodInMinutesSetting(updatePeriodSharedPreferences);

        Switch switchButton = (Switch)findViewById(R.id.start_switch);
		FrequencyTextField frequency = new FrequencyTextField((EditText)findViewById(R.id.frequency), storedUpdateSetting);
		frequency.setTextField(String.valueOf(updatePeriodSetting.getUpdatePeriodInMinutes()));

		switchButton.setChecked(storedUpdateSetting.isUpdatesActive());
		frequency.setEnabledOrDisabledAccordingToUpdateStatus();

		StartSwitchListener startSwitchListener = new StartSwitchListener(getApplicationContext(), storedUpdateSetting, updatePeriodSetting, frequency);
		switchButton.setOnCheckedChangeListener(startSwitchListener);
    }
}
