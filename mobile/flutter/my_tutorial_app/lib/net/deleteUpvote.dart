import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

Future<http.Response> deleteUpvote(int mId) async {
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
  final response = await http.delete(Uri.parse('$url/api/v3.0/messages/upvote/$mId'), headers: headers);
  // process responce
  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return response;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to remove upvote');
  }
}