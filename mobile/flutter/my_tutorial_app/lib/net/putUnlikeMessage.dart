import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;

Future<http.Response> putUnlikeMessage(int id) async {
  //loading .env variables
  await dotenv.load(fileName: "lib/.env");
  final String? url = dotenv.env['BACKEND_URL'];
  // send request
  var headers = {"Accept": "application/json"};
  var body = {};
  final response = await http.put(Uri.parse('$url/api/v3.0/messages/unlike/$id'), headers: headers, body: body);
  // process response
  if (response.statusCode == 200) {
    // If the server did return a 200 OK response,
    // then parse the JSON.
    return response;
  } else {
    // If the server did not return a 200 OK response,
    // then throw an exception.
    throw Exception('Failed to unlike message');
  }
}