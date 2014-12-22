package com.matthewsimonmould.proudmary_v2;

import android.app.PendingIntent;

import junit.framework.TestCase;

public class StartSwitchTest extends TestCase {

    public void testThatWhenRadioButtonIsChecked_AlarmManagerIsSetUp () {
        FakeAlarmManagerWrapper fakeAlarmManagerWrapper = new FakeAlarmManagerWrapper();
        StartSwitchListener listener = new StartSwitchListener(fakeAlarmManagerWrapper);
        listener.onCheckedChanged(null, true);

        assertTrue(fakeAlarmManagerWrapper.setRepeatingInvoked);
    }

    private class FakeAlarmManagerWrapper extends AlarmManagerWrapper {
        boolean setRepeatingInvoked = false;
        @Override
        public void setRepeating(int type, long triggerAtMillis, long intervalMillis, PendingIntent operation) {
            setRepeatingInvoked = true;
        }
    }
}
