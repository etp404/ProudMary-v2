package com.khonsu.enroute.settingup.validator;

import android.os.AsyncTask;

import com.khonsu.enroute.AddressValidator;
import com.khonsu.enroute.settingup.MainView;
import com.khonsu.enroute.util.UiThreadExecutor;

public class FormValidator {
    private FormValidatorCallback callback;
    private UiThreadExecutor uiThreadExecutor;
    private AddressValidator addressValidator;

    public FormValidator(UiThreadExecutor uiThreadExecutor,
                         AddressValidator addressValidator) {
        this.uiThreadExecutor = uiThreadExecutor;
        this.addressValidator = addressValidator;
	}

	public void setCallback(FormValidatorCallback callback) {
		this.callback = callback;
	}

    public void validateForm(MainView mainView) {
        AsyncTask<MainView, Void, Boolean> asyncTask = new AsyncTask<MainView, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(MainView... views) {
                MainView mainView = views[0];
                boolean formValid = true;
                RecipientValidator recipientValidator = new RecipientValidator();
                if (!recipientValidator.validate(mainView.getRecipient())) {
                    formValid = false;
                    showErrorOnRecipient(mainView);
                }
                if (!addressValidator.validate(mainView.getDestination())) {
                    formValid = false;
                    showErrorOnDestination(mainView);
                }
                return formValid;
            }

            @Override
            protected void onPostExecute(Boolean isValid) {
                if (isValid) {
                    callback.success();
                }
            }
            private void showErrorOnRecipient(final MainView mainView) {
                uiThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mainView.showErrorOnRecipient();
                    }
                });
            }
            private void showErrorOnDestination(final MainView mainView) {
                uiThreadExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        mainView.showErrorOnDestination();
                    }
                });
            }
        };

        asyncTask.execute(mainView);
    }

    public interface FormValidatorCallback {
        void success();
    }


}
