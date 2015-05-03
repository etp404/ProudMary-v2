package com.khonsu.enroute.settingup.activities;

import android.widget.CompoundButton;

import com.khonsu.enroute.settingup.MainPresenter;

public class StartSwitchListener implements CompoundButton.OnCheckedChangeListener {
	private MainPresenter mainPresenter;

	public StartSwitchListener(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			mainPresenter.startPressed();
		}
		else {
			mainPresenter.stopPressed();
		}
	}
}
