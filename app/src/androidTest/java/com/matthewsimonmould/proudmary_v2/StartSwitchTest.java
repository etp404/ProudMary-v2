package com.matthewsimonmould.proudmary_v2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.test.AndroidTestCase;

public class StartSwitchTest extends AndroidTestCase {

    public void testThatWhenRadioButtonIsChecked_AlarmManagerIsSetUp () {
        FakeAlarmManagerWrapper fakeAlarmManagerWrapper = new FakeAlarmManagerWrapper();
        StartSwitchListener listener = new StartSwitchListener(getContext(), fakeAlarmManagerWrapper);

        listener.onCheckedChanged(null, true);

        assertEquals(fakeAlarmManagerWrapper.setRepeatingInvokedWithType, AlarmManager.RTC_WAKEUP);
        assertEquals(fakeAlarmManagerWrapper.setRepeatingInvokedWithTriggerAtMillis, 0);
        assertEquals(fakeAlarmManagerWrapper.setRepeatingInvokedWithIntervalMillis, 10000);
        assertNotNull(fakeAlarmManagerWrapper.setRepeatingInvokedWithPendingIntent);
    }

    public void testThatWhenRadioButtonIsUnchecked_AlarmManagerIsCancelled () {
        FakeAlarmManagerWrapper fakeAlarmManagerWrapper = new FakeAlarmManagerWrapper();
        StartSwitchListener listener = new StartSwitchListener(getContext(), fakeAlarmManagerWrapper);

        listener.onCheckedChanged(null, false);

        assertNotNull(fakeAlarmManagerWrapper.cancelInvokedWithPendingIntent);
    }

    private class FakeAlarmManagerWrapper extends AlarmManagerWrapper {

        int setRepeatingInvokedWithType;
        long setRepeatingInvokedWithTriggerAtMillis;
        long setRepeatingInvokedWithIntervalMillis;
        PendingIntent setRepeatingInvokedWithPendingIntent;
        PendingIntent cancelInvokedWithPendingIntent;

        public FakeAlarmManagerWrapper() {
            super(null);
        }

        @Override
        public void setRepeating(int type, long triggerAtMillis, long intervalMillis, PendingIntent pendingIntent) {
            setRepeatingInvokedWithType = type;
            setRepeatingInvokedWithTriggerAtMillis = triggerAtMillis;
            setRepeatingInvokedWithIntervalMillis = intervalMillis;
            setRepeatingInvokedWithPendingIntent = pendingIntent;
        }

        @Override
        public void cancel(PendingIntent pendingIntent) {
            cancelInvokedWithPendingIntent = pendingIntent;
        }

    }
}
