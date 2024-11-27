package com.example.call_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;

public class IncomingCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("IncomingCallReceiver", "Permission not granted");
            return;
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        Log.d("IncomingCallReceiver", "State: " + state + ", Number: " + incomingNumber);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) && incomingNumber != null) {
            Intent serviceIntent = new Intent(context, FloatingService.class);
            serviceIntent.putExtra("number", incomingNumber);
            context.startService(serviceIntent);
        } else {
            Log.d("IncomingCallReceiver", "Incoming number is null or not ringing");
        }
    }
}
