package com.matthewsimonmould.proudmary_v2;

import android.content.SharedPreferences;

public class UpdatePeriodInMinutesSetting {

	public static final String UPDATES_PERIOD_PREFERENCES = "updates_period_preferences";
	private static final String UPDATE_PERIOD_IN_MINUTES = "updatePeriodInMinutes";
	private final SharedPreferences sharedPreferences;

	public UpdatePeriodInMinutesSetting(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	public void setUpdatePeriodInMinutes(int updatePeriodInMinutes) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(UPDATE_PERIOD_IN_MINUTES, updatePeriodInMinutes);
		editor.apply();
	}

	public int getUpdatePeriodInMinutes() {
		return sharedPreferences.getInt(UPDATE_PERIOD_IN_MINUTES, 1);
	}

	public void deleteRecord() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.remove(UPDATE_PERIOD_IN_MINUTES);
	}
}
