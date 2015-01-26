package com.khonsu.proudmary_v2;

import android.telephony.SmsManager;

public class SMSSender {
    public static void sendSMS(String recipientNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(recipientNumber, null, message, null, null);
    }
}