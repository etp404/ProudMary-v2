package com.matthewsimonmould.proudmary_v2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Switch;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch switchButton = (Switch)findViewById(R.id.start_switch);
        AlarmManagerWrapper alarmManagerWrapper = new AlarmManagerWrapper();
        switchButton.setOnCheckedChangeListener(new StartSwitchListener(null, alarmManagerWrapper));
    }
}
