package com.matthewsimonmould.proudmary_v2;

import android.test.AndroidTestCase;

public class SendUpdateBroadcastReceiverTest extends AndroidTestCase {

    public void testThatNotificationIsProvided_WhenReceiveIntentIsInvoked () {
        FakeNotificationManagerWrapper notificationManagerWrapper = new FakeNotificationManagerWrapper();
        SendUpdateBroadcastReceiver broadcastReceiver = new SendUpdateBroadcastReceiver(notificationManagerWrapper);
        broadcastReceiver.onReceive(null, null);
        assertNotNull(notificationManagerWrapper.provideNotificationInvokedWith);
    }


    private class FakeNotificationManagerWrapper extends NotificationManagerWrapper {
        public String provideNotificationInvokedWith;
        @Override
        public void provideNotification(String string) {
            provideNotificationInvokedWith = string;
        }
    }
}
