package com.matthewsimonmould.proudmary_v2.robolectric;

import com.matthewsimonmould.proudmary_v2.NotificationManagerWrapper;
import com.matthewsimonmould.proudmary_v2.SendNotificationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)

public class SendNotificationServiceTest {
    @Test
    public void testThatOnStartNotificationIsGenerated() {
        FakeNotificationManagerWrapper fakeNotificationManagerWrapper = new FakeNotificationManagerWrapper();
        SendNotificationService sendNotificationService = new SendNotificationService(fakeNotificationManagerWrapper);

        sendNotificationService.onStartCommand(null, 0, 0);

        assertNotNull(fakeNotificationManagerWrapper.provideNotificationInvokedWithContentString);
    }

    private class FakeNotificationManagerWrapper extends NotificationManagerWrapper {
        String provideNotificationInvokedWithContentString;
        @Override
        public void provideNotification(String contentText) {
            provideNotificationInvokedWithContentString = contentText;
        }
    }

}
