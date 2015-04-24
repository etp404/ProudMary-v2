package com.khonsu.enroute;


import com.khonsu.enroute.contactsautocomplete.Contact;
import com.khonsu.enroute.settings.UpdaterSettings;

public class MainPresenter {

	private UpdaterSettings updaterSettings;
	private MainView mainView;

	public MainPresenter(UpdaterSettings updaterSettings, final MainView mainView) {
		this.updaterSettings = updaterSettings;
		this.mainView = mainView;
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
		storeForm();
		mainView.makeFormActive(false);
		updaterSettings.setUpdatesActive(true);
	}

	public void stopPressed() {
		mainView.makeFormActive(true);
		updaterSettings.setUpdatesActive(false);
	}

	private void storeForm() {
		updaterSettings.setRecipient(Contact.fromString(mainView.getRecipient()));
		updaterSettings.setDestination(mainView.getDestination());
		updaterSettings.setTransportMode(ModeOfTransport.getEnum(mainView.getModeOfTravel()));
		updaterSettings.setUpdatePeriodInMinutes(mainView.getUpdatePeriodInMinutes());
	}
}
