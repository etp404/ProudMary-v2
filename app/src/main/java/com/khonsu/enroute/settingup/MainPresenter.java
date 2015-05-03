package com.khonsu.enroute.settingup;

import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.sending.scheduling.UpdateScheduler;
import com.khonsu.enroute.usernotifications.Notifier;
import com.khonsu.enroute.util.Navigator;
import com.khonsu.enroute.util.NetworkStatus;
import com.khonsu.enroute.usernotifications.Toaster;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.settingup.contacts.Contact;
import com.khonsu.enroute.settingup.validator.FormValidator;

public class MainPresenter {
	private static final String NO_INTERNET_CONNECTION = "Please ensure network is enabled.";

	private final NetworkStatus networkStatus;
	private Notifier notifier;
	private final Toaster toaster;

	private UpdaterSettings updaterSettings;
	private MainView mainView;
	private FormValidator formValidator;
	private UpdateScheduler updateScheduler;
	private Navigator navigator;

	public MainPresenter(NetworkStatus networkStatus,
						 Notifier notifier,
						 Toaster toaster,
						 UpdaterSettings updaterSettings,
						 Navigator navigator,
						 final MainView mainView,
						 FormValidator formValidator,
						 UpdateScheduler updateScheduler) {
		this.networkStatus = networkStatus;
		this.notifier = notifier;
		this.toaster = toaster;
		this.updaterSettings = updaterSettings;
		this.navigator = navigator;
		this.mainView = mainView;
		this.formValidator = formValidator;
		this.updateScheduler = updateScheduler;
	}

	public void populateView() {
		mainView.setRecipient(updaterSettings.getRecipient().toString());
		mainView.setDestination(updaterSettings.getDestination());
		mainView.setTransportMode(updaterSettings.getTransportMode());
		mainView.setUpdatePeriodInMinutes(updaterSettings.getUpdatePeriodInMinutes());
		mainView.setUpdatesActive(updaterSettings.isUpdatesActive());

		mainView.makeFormActive(!updaterSettings.isUpdatesActive());
	}

	public void startPressed() {
		if (!networkStatus.isAvailable()) {
			toaster.toast(NO_INTERNET_CONNECTION);
			rejectForm();
		}
		else {
			formValidator.setListener(new FormValidator.FormValidatorListener() {
				@Override
				public void success() {
					startSendingUpdates();
				}

				@Override
				public void failure() {
					rejectForm();
				}
			});
			formValidator.validateForm(mainView);
		}
	}

	private void rejectForm() {
		mainView.setUpdatesActive(false);
	}

	private void startSendingUpdates() {
		storeForm();
		mainView.makeFormActive(false);
		updaterSettings.setUpdatesActive(true);
		navigator.startUpdateService();
	}

	public void stopPressed() {
		mainView.makeFormActive(true);
		notifier.clearNotification();
		updaterSettings.setUpdatesActive(false);
	}

	private void storeForm() {
		updaterSettings.setRecipient(Contact.fromString(mainView.getRecipient()));
		updaterSettings.setDestination(mainView.getDestination());
		updaterSettings.setTransportMode(ModeOfTransport.getEnum(mainView.getModeOfTravel()));
		updaterSettings.setUpdatePeriodInMinutes(mainView.getUpdatePeriodInMinutes());
	}
}
