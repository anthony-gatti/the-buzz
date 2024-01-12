import 'package:my_tutorial_app/net/getUpvote.dart';
import 'package:test/test.dart';

void main() {
  test('should return a valid upvote', () async {
    final result = await getUpvote(1);
    // testing that the Message recieved has no null fields
    expect(result?.uvId != null, true);
    expect(result?.uId != null, true);
    expect(result?.mId != null, true);
  });
}
