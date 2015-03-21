package com.khonsu.enroute.settings;

import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

public class UpdaterSettings {

	public static final String UPDATER_SETTINGS = "updates_period_preferences";
	private static final String TIME_FOR_NEXT_UPDATE_IN_MILLIS = "time_for_next_update_in_millis";
	private static final String UPDATES_ACTIVE_SETTING = "updates_active_setting";
	private static final String UPDATE_PERIOD_IN_MINUTES = "update_period_in_minutes";
	private static final String UPDATE_DESTINATION = "update_destination";
	private static final String UPDATE_RECIPIENT = "update_recipient";
    private static final String UPDATE_MODE_OF_TRANSPORT = "update_mode_of_transport";

    private final SharedPreferences sharedPreferences;

	public UpdaterSettings(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	public void setTimeForNextUpdateInMillis(long timeForNextUpdateInMillis) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(TIME_FOR_NEXT_UPDATE_IN_MILLIS, timeForNextUpdateInMillis);
		editor.apply();
	}

	public long getTimeForNextUpdateInMillis() {
		return sharedPreferences.getLong(TIME_FOR_NEXT_UPDATE_IN_MILLIS, 0);
	}

	public void setUpdatePeriodInMinutes(int updatePeriodInMinutes) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(UPDATE_PERIOD_IN_MINUTES, updatePeriodInMinutes);
		editor.apply();
	}

	public Integer getUpdatePeriodInMinutes() {
		return sharedPreferences.getInt(UPDATE_PERIOD_IN_MINUTES, 30);
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

    public void setTransportMode(int transportMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(UPDATE_MODE_OF_TRANSPORT, transportMode);
        editor.apply();
    }

    public int getTransportMode() {
        return sharedPreferences.getInt(UPDATE_MODE_OF_TRANSPORT, -1);
    }

	public Long getUpdatePeriodInMillis() {
		return TimeUnit.MINUTES.toMillis(getUpdatePeriodInMinutes());
	}

	public void setUpdatesActive(boolean updatesActive) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(UPDATES_ACTIVE_SETTING, updatesActive);
		editor.apply();
	}

	public boolean isUpdatesActive() {
		return sharedPreferences.getBoolean(UPDATES_ACTIVE_SETTING, false);
	}
}
