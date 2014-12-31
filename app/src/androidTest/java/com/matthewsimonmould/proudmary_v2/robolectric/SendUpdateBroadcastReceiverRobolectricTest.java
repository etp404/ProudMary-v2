package com.matthewsimonmould.proudmary_v2.robolectric;

import android.content.BroadcastReceiver;
import android.content.Intent;

import com.matthewsimonmould.proudmary_v2.SendNotificationService;
import com.matthewsimonmould.proudmary_v2.SendUpdateBroadcastReceiver;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class SendUpdateBroadcastReceiverRobolectricTest {
    @Test
    public void testBroadcastReceiverRegistered() {
        List<ShadowApplication.Wrapper> registeredReceivers = Robolectric.getShadowApplication().getRegisteredReceivers();

        Assert.assertFalse(registeredReceivers.isEmpty());

        boolean receiverFound = false;
        for (ShadowApplication.Wrapper wrapper : registeredReceivers) {
            if (!receiverFound)
                receiverFound = SendUpdateBroadcastReceiver.class.getSimpleName().equals(
                        wrapper.broadcastReceiver.getClass().getSimpleName());
        }

        Assert.assertTrue(receiverFound);
    }

    @Test
    public void testBroadcastReceiverBehavesAsIntended() {
        Intent intent = new Intent("com.matthewsimonmould.proudmary_v2.robolectric.START_PRESSED");
        ShadowApplication shadowApplication = Robolectric.getShadowApplication();
        Assert.assertTrue(shadowApplication.hasReceiverForIntent(intent));

        List<BroadcastReceiver> receiversForIntent = shadowApplication.getReceiversForIntent(intent);

        SendUpdateBroadcastReceiver receiver = (SendUpdateBroadcastReceiver) receiversForIntent.get(0);
        receiver.onReceive(Robolectric.getShadowApplication().getApplicationContext(), intent);

        Intent serviceIntent = Robolectric.getShadowApplication().peekNextStartedService();

        Assert.assertEquals("Expected the MyBroadcast service to be invoked",
                SendNotificationService.class.getCanonicalName(),
                serviceIntent.getComponent().getClassName());
    }
}