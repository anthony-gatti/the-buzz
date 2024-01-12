import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
//import 'package:my_tutorial_app/net/getMessage.dart';

Future<http.Response> putLikeMessage(int id) async {
  //loading .env variables
  await dotenv.load(fileName: "lib/.env");
  final String? url = dotenv.env['BACKEND_URL'];
  // send request
  var headers = {"Accept": "application/json"};
  var body = {};
  final response = await http.put(Uri.parse('$url/api/v3.0/messages/like/$id'), headers: headers, body: body);
  // process responce
  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return response;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to like message. Status Code: ${response.statusCode}, Response: ${response.body}');
  }
}