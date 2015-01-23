package com.matthewsimonmould.proudmary_v2.uifields;

import com.matthewsimonmould.proudmary_v2.settings.StoredUpdateSetting;

import java.util.ArrayList;
import java.util.List;

public class FrequencyNumberPicker {
	private final android.widget.NumberPicker numberPicker;
	private final StoredUpdateSetting storedUpdateSetting;
	private final List<String> incrementValues;

	public FrequencyNumberPicker(android.widget.NumberPicker numberPicker, StoredUpdateSetting storedUpdateSetting) {
		incrementValues = new ArrayList<>();
		for (int i = 5; i <= 60; i+=5)  {
			incrementValues.add(String.valueOf(i));
		}
		this.numberPicker = numberPicker;
		numberPicker.setDisplayedValues(incrementValues.toArray(new String[incrementValues.size()]));
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(incrementValues.size()-1);
		this.storedUpdateSetting = storedUpdateSetting;
	}

	public Integer getUpdateInMinutes() {
		return Integer.valueOf(incrementValues.get(numberPicker.getValue()));
	}

	public void setEnabledOrDisabledAccordingToUpdateStatus() {
		numberPicker.setEnabled(!storedUpdateSetting.isUpdatesActive());
	}

	public void setSelectedFrequency(Integer updatePeriodInMinutes) {
		numberPicker.setValue(incrementValues.indexOf(String.valueOf(updatePeriodInMinutes)));
	}
}
