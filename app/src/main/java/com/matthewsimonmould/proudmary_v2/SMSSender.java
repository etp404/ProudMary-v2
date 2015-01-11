package com.matthewsimonmould.proudmary_v2;

import android.telephony.SmsManager;

public class SMSSender {
    public static void sendSMS(String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("07791851385", null, message, null, null); //TODO: ability to set number.
    }
}
