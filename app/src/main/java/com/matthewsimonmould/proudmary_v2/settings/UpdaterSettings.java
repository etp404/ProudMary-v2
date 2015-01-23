package com.matthewsimonmould.proudmary_v2.settings;

import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

public class UpdaterSettings {

	public static final String UPDATER_SETTINGS = "updates_period_preferences";
	private static final String UPDATE_PERIOD_IN_MINUTES = "update_period_in_minutes";
	private static final String UPDATE_DESTINATION = "update_destination";
	private static final String UPDATE_RECIPIENT = "update_recipient";

	private final SharedPreferences sharedPreferences;

	public UpdaterSettings(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	public void setUpdatePeriodInMinutes(int updatePeriodInMinutes) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(UPDATE_PERIOD_IN_MINUTES, updatePeriodInMinutes);
		editor.apply();
	}

	public String getUpdatePeriodInMinutes() {
		return sharedPreferences.getString(UPDATE_PERIOD_IN_MINUTES, "");
	}

	public void setDestination(String destination) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(UPDATE_DESTINATION, destination);
		editor.apply();
	}

	public String getDestination() {
		return sharedPreferences.getString(UPDATE_DESTINATION, "");
	}

	public void setRecipient(String recipient) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(UPDATE_RECIPIENT, recipient);
		editor.apply();
	}

	public String getRecipient() {
		return sharedPreferences.getString(UPDATE_RECIPIENT, "");
	}

	public Long getUpdatePeriodInMillis() {
		return TimeUnit.MINUTES.toMillis(Integer.parseInt(getUpdatePeriodInMinutes()));
	}
}