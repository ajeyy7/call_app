import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Call App',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: CallHomePage(),
    );
  }
}

class CallHomePage extends StatefulWidget {
  @override
  _CallHomePageState createState() => _CallHomePageState();
}

class _CallHomePageState extends State<CallHomePage> {
  static const platform = MethodChannel('call.channel');

  final List<Map<String, String>> contacts = [
    {'number': '+919074215610', 'name': 'JohnWick'},
    {'number': '+918593949886', 'name': 'Abraham Lincoln'},
    {'number': '+918547964709', 'name': 'Donald Trump'},
    {'number': '+919846572149', 'name': 'John Seena'},
  ];

  @override
  void initState() {
    super.initState();
    _requestPermissions();
    _sendContactsToNative();
  }

  Future<void> _requestPermissions() async {
    // Requesting permissions for phone state and call log
    final statuses = await [
      Permission.phone,
      Permission.contacts,
    ].request();

    // Check if SYSTEM_ALERT_WINDOW is granted
    if (!(await Permission.systemAlertWindow.isGranted)) {
      await Permission.systemAlertWindow.request();
    }

    if (statuses[Permission.phone]?.isGranted == true &&
        statuses[Permission.contacts]?.isGranted == true) {
      _sendContactsToNative();
    } else {
      print("Required permissions are not granted.");
    }
  }

  Future<void> _sendContactsToNative() async {
    try {
      await platform.invokeMethod('sendContacts', contacts);
    } on PlatformException catch (e) {
      print("Failed to send contacts: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Call Detector"),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Center(
            child: Text("Waiting for incoming call..."),
          ),
          ElevatedButton(
            onPressed: () {
              openAppSettings();
            },
            child: Text("Open App Settings"),
          )
        ],
      ),
    );
  }
}
