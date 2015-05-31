package com.khonsu.enroute.settingup.contacts;

import android.os.Handler;
import android.os.Looper;

import com.khonsu.enroute.settingup.MainView;

public class ContactsAccessorListener implements ContactsAccessor.Listener {
	private final MainView mainView;
	private Handler mainHandler;

	public ContactsAccessorListener(MainView mainView) {
		mainHandler = new Handler(Looper.getMainLooper());
		this.mainView = mainView;
	}
	@Override
	public void startingToFetchContacts() {
		mainHandler.post(new Runnable() {
			@Override
			public void run() {
				mainView.showContactsLoadingSpinner();
			}
		});
	}

	@Override
	public void finishedFetchingContacts() {
		mainHandler.post(new Runnable() {
			@Override
			public void run() {
				mainView.hideLoadingSpinner();
			}
		});
	}
}
