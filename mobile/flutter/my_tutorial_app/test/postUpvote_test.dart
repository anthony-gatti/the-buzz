import 'package:my_tutorial_app/net/postUpvote.dart';
import 'package:my_tutorial_app/net/getUpvote.dart';
import 'package:test/test.dart';

void main() {
  test('should post a comment', () async {
    await postUpvote(1);
    var response = await getUpvote(1);
    // testing the last message in the list is the one we just added
    expect(response?.uvId != null, true);
  });
}
