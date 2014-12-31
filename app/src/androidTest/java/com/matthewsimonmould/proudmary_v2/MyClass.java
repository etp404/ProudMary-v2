package com.matthewsimonmould.proudmary_v2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class MyClass {
    @Test
    public void testBroadcastReceiverRegistered() {
        List<ShadowApplication.Wrapper> registeredReceivers = Robolectric.getShadowApplication().getRegisteredReceivers();

        Assert.assertFalse(registeredReceivers.isEmpty());

        boolean receiverFound = false;
        for (ShadowApplication.Wrapper wrapper : registeredReceivers) {
//            if (!receiverFound)
//                receiverFound = SendUpdateBroadcastReceiver.class.getSimpleName().equals(
//                        wrapper.broadcastReceiver.getClass().getSimpleName());
        }

        Assert.assertTrue(receiverFound); //will be false if not found
    }
}
