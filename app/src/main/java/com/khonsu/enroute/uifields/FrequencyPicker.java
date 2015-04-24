package com.khonsu.enroute.uifields;

import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

public class FrequencyPicker {
	private final NumberPicker numberPicker;
	private final List<String> incrementValues;

	public FrequencyPicker(NumberPicker numberPicker) {
		incrementValues = new ArrayList<>();
		for (int i = 5; i <= 60; i+=5)  {
			incrementValues.add(String.valueOf(i));
		}
		this.numberPicker = numberPicker;
		numberPicker.setDisplayedValues(incrementValues.toArray(new String[incrementValues.size()]));
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(incrementValues.size()-1);
	}

	public Integer getUpdateInMinutes() {
		return Integer.valueOf(incrementValues.get(numberPicker.getValue()));
	}

	public void setEnabled(boolean active) {
		numberPicker.setEnabled(active);
	}

	public void setUpdatePeriodInMinute(Integer updatePeriodInMinutes) {
		numberPicker.setValue(incrementValues.indexOf(String.valueOf(updatePeriodInMinutes)));
	}

}
