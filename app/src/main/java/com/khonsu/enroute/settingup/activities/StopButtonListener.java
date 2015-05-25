package com.khonsu.enroute.settingup.activities;

import android.view.View;
import android.widget.Button;

import com.khonsu.enroute.settingup.MainPresenter;

public class StopButtonListener implements Button.OnClickListener {
	private MainPresenter mainPresenter;

	public StopButtonListener(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}


	@Override
	public void onClick(View v) {
		mainPresenter.stopPressed();
	}
}
