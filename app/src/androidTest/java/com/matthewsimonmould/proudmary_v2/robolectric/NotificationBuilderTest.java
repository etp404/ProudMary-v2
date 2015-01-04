package com.matthewsimonmould.proudmary_v2.robolectric;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;

import com.matthewsimonmould.proudmary_v2.NotificationBuilder;
import com.matthewsimonmould.proudmary_v2.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(RobolectricTestRunner.class)
public class NotificationBuilderTest {
    @Test
    @TargetApi(19)
    //The app itself supports up to API Level 18, but this test will run a newer version.
    public void testThatNotificationIsBuiltAsExpected() {

        String contentTitle = "Proud Mary";
        String contentText = "Hello, I am a message";
        PendingIntent shadowPendingIntent = PendingIntent.getActivities(null, 0, null, 0);

        Notification actualNotification = NotificationBuilder.build(Robolectric.getShadowApplication().getApplicationContext(), shadowPendingIntent, contentTitle, contentText);
        assertEquals(contentTitle, actualNotification.extras.getString(Notification.EXTRA_TITLE));
        assertEquals(contentText, actualNotification.extras.getString(Notification.EXTRA_TEXT));
        assertEquals(R.drawable.pm_logo, actualNotification.extras.getInt(Notification.EXTRA_SMALL_ICON));
        assertEquals(Notification.DEFAULT_LIGHTS, actualNotification.defaults);
        assertSame(shadowPendingIntent, actualNotification.contentIntent);
    }

}
