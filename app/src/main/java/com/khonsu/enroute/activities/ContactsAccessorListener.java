package com.khonsu.enroute.activities;

import android.os.Handler;
import android.os.Looper;

import com.khonsu.enroute.MainView;
import com.khonsu.enroute.contactsautocomplete.ContactsAccessor;

class ContactsAccessorListener implements ContactsAccessor.ContactsAccessorListener {
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
				mainView.showLoadingSpinner();
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
