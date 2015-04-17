package com.khonsu.enroute.activities;

import android.widget.CompoundButton;

import com.khonsu.enroute.MainView;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {
	private MainView mainView;

	public StartSwitchListener(MainView mainView) {
		this.mainView = mainView;
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			mainView.makeFormActive(false);
		}
		else {
			mainView.makeFormActive(true);
		}
	}
}
