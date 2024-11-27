package com.example.call_app;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "call.channel";
    public static HashMap<String, String> contacts = new HashMap<>();

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler(
                        (call, result) -> {
                            if (call.method.equals("sendContacts")) {
                                List<Map<String, String>> contactList = call.arguments();
                                updateContacts(contactList);
                                result.success(null);
                            } else {
                                result.notImplemented();
                            }
                        }
                );
    }

    private void updateContacts(List<Map<String, String>> contactList) {
        contacts.clear();
        for (Map<String, String> contact : contactList) {
            contacts.put(contact.get("number"), contact.get("name"));
        }
    }
}
