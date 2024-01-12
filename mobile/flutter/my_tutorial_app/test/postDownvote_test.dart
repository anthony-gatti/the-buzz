import 'package:my_tutorial_app/net/postDownvote.dart';
import 'package:my_tutorial_app/net/getDownvote.dart';
import 'package:test/test.dart';

void main() {
  test('should post a comment', () async {
    await postDownvote(1);
    var response = await getDownvote(1);
    // testing the last message in the list is the one we just added
    expect(response?.dvId != null, true);
  });
}
