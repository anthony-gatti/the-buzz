import 'package:my_tutorial_app/net/postComment.dart';
import 'package:my_tutorial_app/net/getComments.dart';
import 'package:test/test.dart';

void main() {
  test('should post a comment', () async {
    await postComment(1, "Mobile Automated Test");
    var response = await getComments(1);
    var lastElement = response[response.length-1];
    // testing the last message in the list is the one we just added
    expect(lastElement.content!.compareTo("Mobile Automated Test")==0, true);
  });
}
