package com.example.call_app;

import android.app.Application;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.dart.DartExecutor;

public class App extends Application {
    public static FlutterEngine flutterEngine;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Flutter Engine with proper entrypoint
        flutterEngine = new FlutterEngine(this);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        );
    }
}
