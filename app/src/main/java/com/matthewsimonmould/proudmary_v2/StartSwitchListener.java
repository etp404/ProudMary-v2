package com.matthewsimonmould.proudmary_v2;

import android.widget.CompoundButton;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {

    private AlarmManagerWrapper alarmManagerWrapper;

    public StartSwitchListener(AlarmManagerWrapper alarmManagerWrapper) {
        this.alarmManagerWrapper = alarmManagerWrapper;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        alarmManagerWrapper.setRepeating(0, 0, 0, null);
    }
}
