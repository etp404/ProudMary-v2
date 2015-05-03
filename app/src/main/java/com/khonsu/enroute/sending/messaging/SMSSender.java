package com.khonsu.enroute.sending.messaging;

import android.telephony.SmsManager;

import java.util.ArrayList;

public class SMSSender {
    public static void sendSMS(String recipientNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);
        if (parts.size() == 1) {
            sms.sendTextMessage(recipientNumber, null, message, null, null);
        }
        else {
            sms.sendMultipartTextMessage(recipientNumber, null, parts, null, null);
        }
    }
}
