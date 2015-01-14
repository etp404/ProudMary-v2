package com.matthewsimonmould.proudmary_v2;

import android.content.SharedPreferences;

public class StoredUpdateSetting {

	public static final String UPDATES_ACTIVE_PREFERENCES = "updates_active_preferences";

	private final SharedPreferences sharedPreferences;
	private static final String updatesActiveSetting = "updatesActiveSetting";

	public StoredUpdateSetting(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	public void setUpdatesActive(boolean updatesActive) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(updatesActiveSetting, updatesActive);
		editor.apply();
	}

	public boolean isUpdatesActive() {
		return sharedPreferences.getBoolean(updatesActiveSetting, false);
	}
}
