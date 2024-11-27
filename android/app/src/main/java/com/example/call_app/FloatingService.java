package com.example.call_app;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class FloatingService extends Service {
    private WindowManager windowManager;
    private View floatingView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String incomingNumber = intent.getStringExtra("number");
        Log.d("FloatingService", "Incoming number: " + incomingNumber);

        String callerName = MainActivity.contacts.getOrDefault(incomingNumber, "Unknown Caller");

        // Inflate the floating widget layout
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget, null);

        // Set caller name and number
        TextView callerNameView = floatingView.findViewById(R.id.caller_name);
        TextView callerNumberView = floatingView.findViewById(R.id.caller_number);
        ImageButton closeButton = floatingView.findViewById(R.id.close_button);

        callerNameView.setText(callerName);
        callerNumberView.setText(incomingNumber);

        // Close button click listener
        closeButton.setOnClickListener(v -> stopSelf());

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,  // Full width
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(floatingView, params);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) {
            windowManager.removeView(floatingView);
        }
    }
}
