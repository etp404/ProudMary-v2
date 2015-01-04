package com.matthewsimonmould.proudmary_v2.robolectric;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;

import com.matthewsimonmould.proudmary_v2.NotificationBuilder;
import com.matthewsimonmould.proudmary_v2.NotificationManagerWrapper;
import com.matthewsimonmould.proudmary_v2.SendNotificationService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertSame;

@RunWith(RobolectricTestRunner.class)
public class SendNotificationServiceTest {
    @Test
    public void testThatOnStartNotificationIsGenerated() {
        FakeNotificationManagerWrapper fakeNotificationManagerWrapper = new FakeNotificationManagerWrapper();
        FakeNotificationBuilder fakeNotificationBuilder = new FakeNotificationBuilder();
        fakeNotificationBuilder.notificationToBeReturned = new Notification();

        SendNotificationService sendNotificationService = new SendNotificationService(fakeNotificationManagerWrapper, fakeNotificationBuilder);

        sendNotificationService.onStartCommand(null, 0, 0);

        assertSame(fakeNotificationBuilder.notificationToBeReturned,
                fakeNotificationManagerWrapper.provideNotificationInvokedWithNotification);
    }

    private class FakeNotificationManagerWrapper extends NotificationManagerWrapper {
        Notification provideNotificationInvokedWithNotification;
        @Override
        public void provideNotification(Notification notification) {
            provideNotificationInvokedWithNotification = notification;
        }
    }

    private class FakeNotificationBuilder extends NotificationBuilder {
        Notification notificationToBeReturned;

        @Override
        public Notification build(Context context, PendingIntent launchNotification, String contentTitle, String contentText) {
            return notificationToBeReturned;
        }
    }

}
