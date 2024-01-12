import 'dart:typed_data';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter/services.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:like_button/like_button.dart';
import 'package:my_tutorial_app/models/Message.dart';
import 'package:my_tutorial_app/net/getMessages.dart';
import 'dart:io';

import 'package:my_tutorial_app/net/postMessage.dart';

import 'package:my_tutorial_app/login_api.dart';
import 'package:my_tutorial_app/pages/home_page.dart';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
// import 'dart:developer' as developer;

void main() async {
  // loading encryption cert
  WidgetsFlutterBinding.ensureInitialized();
  ByteData data = await PlatformAssetBundle().load('assets/ca/lets-encrypt-r3.pem');
  SecurityContext.defaultContext.setTrustedCertificatesBytes(data.buffer.asUint8List());
  // loading app
  runApp(const MyApp());
}

fromJson(Map<String,dynamic> json) async {
    String session_id = json['mSessionToken'];
    int uId = json['uid'];
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.setString('Session-Key', session_id);
    prefs.setInt('uId', uId);
  }

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'The Buzz App',
      theme: ThemeData(
        // This is the theme of your application.

        colorScheme: ColorScheme.fromSeed(seedColor: Colors.brown),
        useMaterial3: true,
      ),
      home: const LoginPage(),//const MyHomePage(title: 'The Buzz'),
    );
  }
}

// Class to build the login page
class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Login Google"),
      ),
      body: SizedBox(
        width: double.infinity,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            //Sends a request to the login api
            ElevatedButton(
              onPressed: () async {
                final user = await LoginAPi.login();
              GoogleSignInAuthentication asd = await user!.authentication;
                 //catches the id token returned from the backend
                 String? idtoken = asd.idToken;
                 print(idtoken);
                 print(idtoken!.substring(1023));
                 await dotenv.load(fileName: "lib/.env");
                final String? url = dotenv.env['BACKEND_URL'];
                final String? sessionKey = dotenv.env['SESSION_KEY'];
                // Either logs in or creates the user
                final response = await http.post(Uri.parse('$url/api/v3.0/users'), headers: {'Content-Type': 'application/json', 'Id-Token-String': idtoken, "Session-Key": sessionKey.toString()});
                fromJson(jsonDecode(response.body));
                if(response.statusCode != 200){
                  print("User not found");
                }else{
                  print("Success!");
                  SharedPreferences prefs = await SharedPreferences.getInstance();
                  final int? uId = prefs.getInt('uId');
                  print(uId);
                  //Sends user to homepage
                  Navigator.push(context, MaterialPageRoute(builder: (context) => MyHomePage(title: 'The Buzz', uId: uId!)));
                }
              }, 
              child: const Text("Login with Google")
            )
          ]
        ),
      ),
    );
  }
}


Future<List<String>> simpleLongRunningCalculation() async {
  await Future.delayed(Duration(seconds: 5)); // wait 5 sec
  List<String> myList = List.generate(100, 
                        (index) => WordPair.random().asPascalCase,
                        growable: true);
  return myList;
  // return ['x', 'y', 'z']; // you can hard code the result if you want it more predictable
}