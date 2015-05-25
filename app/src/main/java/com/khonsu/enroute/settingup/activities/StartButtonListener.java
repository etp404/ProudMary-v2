package com.khonsu.enroute.settingup.activities;

import android.view.View;
import android.widget.Button;

import com.khonsu.enroute.settingup.MainPresenter;

public class StartButtonListener implements Button.OnClickListener {
	private MainPresenter mainPresenter;

	public StartButtonListener(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}


	@Override
	public void onClick(View v) {
		mainPresenter.startPressed();
	}
}
