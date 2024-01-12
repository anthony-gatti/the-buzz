import 'package:my_tutorial_app/net/postMessage.dart';
import 'package:my_tutorial_app/net/getMessages.dart';
import 'package:test/test.dart';

void main() {
  test('should post a message', () async {
    await postMessage("Mobile Test", "This is a POST test");
    var response = await getMessages();
    var lastElement = response[response.length-1];
    // testing the last message in the list is the one we just added
    expect(lastElement.subject!.compareTo("Mobile Test")==0, true);
    expect(lastElement.message!.compareTo("This is a POST test")==0, true);
  });
}
