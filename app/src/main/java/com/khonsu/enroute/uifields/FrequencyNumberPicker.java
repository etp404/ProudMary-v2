package com.khonsu.enroute.uifields;

import android.widget.NumberPicker;

import com.khonsu.enroute.settings.UpdaterSettings;

import java.util.ArrayList;
import java.util.List;

public class FrequencyNumberPicker {
	private final NumberPicker numberPicker;
	private final UpdaterSettings updaterSettings;
	private final List<String> incrementValues;

	public FrequencyNumberPicker(NumberPicker numberPicker, UpdaterSettings updaterSettings) {
		incrementValues = new ArrayList<>();
		for (int i = 5; i <= 60; i+=5)  {
			incrementValues.add(String.valueOf(i));
		}
		this.numberPicker = numberPicker;
		numberPicker.setDisplayedValues(incrementValues.toArray(new String[incrementValues.size()]));
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(incrementValues.size()-1);
		this.updaterSettings = updaterSettings;
	}

	public Integer getUpdateInMinutes() {
		return Integer.valueOf(incrementValues.get(numberPicker.getValue()));
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		numberPicker.setEnabled(!updaterSettings.isUpdatesActive());
	}

	public void setSelectedFrequency(Integer updatePeriodInMinutes) {
		numberPicker.setValue(incrementValues.indexOf(String.valueOf(updatePeriodInMinutes)));
	}
}
