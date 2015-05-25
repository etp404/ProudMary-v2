package com.khonsu.enroute.settingup;

import com.khonsu.enroute.ModeOfTransport;
import com.khonsu.enroute.util.NetworkStatus;
import com.khonsu.enroute.usernotifications.Toaster;
import com.khonsu.enroute.settings.UpdaterSettings;
import com.khonsu.enroute.settingup.contacts.Contact;
import com.khonsu.enroute.settingup.validator.FormValidator;

public class MainPresenter {
	private static final String NO_INTERNET_CONNECTION = "Please ensure network is enabled.";

	private BroadcastSender broadcastSender;
	private final NetworkStatus networkStatus;
	private final Toaster toaster;

	private UpdaterSettings updaterSettings;
	private MainView mainView;
	private FormValidator formValidator;

	public MainPresenter(
						BroadcastSender broadcastSender,
						NetworkStatus networkStatus,
						 Toaster toaster,
						 UpdaterSettings updaterSettings,
						 final MainView mainView,
						 FormValidator formValidator) {
		this.broadcastSender = broadcastSender;
		this.networkStatus = networkStatus;
		this.toaster = toaster;
		this.updaterSettings = updaterSettings;
		this.mainView = mainView;
		this.formValidator = formValidator;
	}

	public void populateView() {
		mainView.setRecipient(updaterSettings.getRecipient().toString());
		mainView.setDestination(updaterSettings.getDestination());
		mainView.setTransportMode(updaterSettings.getTransportMode());
		mainView.setUpdatePeriodInMinutes(updaterSettings.getUpdatePeriodInMinutes());
		if (updaterSettings.isUpdatesActive()) {
			setViewForActiveUpdates();
		}
		else {
			setViewForInactiveUpdates();
		}
	}

	private void setViewForInactiveUpdates() {
		mainView.makeFormActive();
		mainView.hideStopButton();
		mainView.showStartButton();
	}

	private void setViewForActiveUpdates() {
		mainView.makeFormInactive();
		mainView.hideStartButton();
		mainView.showStopButton();
	}

	public void startPressed() {
		if (!networkStatus.isAvailable()) {
			toaster.toast(NO_INTERNET_CONNECTION);
		}
		else {
			formValidator.setCallback(new FormValidator.FormValidatorCallback() {
				@Override
				public void success() {
					startSendingUpdates();
				}

			});
			formValidator.validateForm(mainView);
		}
	}

	private void startSendingUpdates() {
		storeForm();
		updaterSettings.setUpdatesActive(true);
		setViewForActiveUpdates();
		broadcastSender.sendUpdate();
	}

	public void sendingStopped() {
		setViewForInactiveUpdates();
	}

	private void storeForm() {
		updaterSettings.setRecipient(Contact.fromString(mainView.getRecipient()));
		updaterSettings.setDestination(mainView.getDestination());
		updaterSettings.setTransportMode(ModeOfTransport.getEnum(mainView.getModeOfTravel()));
		updaterSettings.setUpdatePeriodInMinutes(mainView.getUpdatePeriodInMinutes());
	}

	public void stopPressed() {
		broadcastSender.stopUpdates();
	}
}
