package com.khonsu.enroute.validator;

import android.os.AsyncTask;

import com.khonsu.enroute.uifields.TextField;

public class FormValidator extends AsyncTask<TextField, Void, Boolean> {
    private FormValidatorListener listener;
    private boolean formValid;

    public FormValidator(FormValidatorListener listener) {

        this.listener = listener;
    }
    @Override
    protected Boolean doInBackground(TextField... textFields) {
        return validateForm(textFields);
    }

    @Override
    protected void onPostExecute(Boolean isValid) {
        if (isValid) {
            listener.success();
        }
        else {
            listener.failure();
        }
    }

    private boolean validateForm(TextField... textFields) {

        formValid = true;
        for (TextField textField : textFields)
        if (!textField.validate()) {
            formValid = false;
        }
        return formValid;
    }

    public interface FormValidatorListener {
        public void success();
        public void failure();
    }
}
