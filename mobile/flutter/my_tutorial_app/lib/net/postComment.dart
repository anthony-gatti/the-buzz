import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:io';

Future<http.Response> postComment(int mId, String content, String? url, File? file) async {
  //loading .env variables
  await dotenv.load(fileName: "lib/.env");
  final String? url = dotenv.env['BACKEND_URL'];
  SharedPreferences prefs = await SharedPreferences.getInstance();
  final String? sessionKey = prefs.getString('Session-Key');  // send request
  // send request
  var headers = {
    "Content-Type": "application/json",
    "Session-Key": sessionKey.toString(),
  };
  var body = jsonEncode(<String, String>{
    'cContent': content,
    'cURL': url ?? '',
  });

  // If a file is provided, read its content and encode it
  if (file != null) {
    List<int> fileBytes = await file.readAsBytes();
    String b64File= base64Encode(fileBytes);

    // Concatenate the file info directly to the body string
    body += '''
    "fName": "${file.path.split('/').last}",
    "mFile": "$b64File",
  ''';
  }

  final response = await http.post(Uri.parse('$url/api/v3.0/comments/$mId'), headers: headers, body: body);
  // process responce
  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return response;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to post comment');
  }
}