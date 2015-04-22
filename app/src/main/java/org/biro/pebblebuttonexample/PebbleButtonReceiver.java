package org.biro.pebblebuttonexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PebbleButtonReceiver extends BroadcastReceiver {
    MainActivity parent = null;

    public PebbleButtonReceiver(MainActivity ma) {
        parent = ma;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int button = intent.getIntExtra("Button", -1);
        parent.buttonPress(button);
    }
}
