import 'package:my_tutorial_app/net/getDownvote.dart';
import 'package:test/test.dart';

void main() {
  test('should return a valid downVote', () async {
    final result = await getDownvote(1);
    // testing that the Message recieved has no null fields
    expect(result?.dvId != null, true);
    expect(result?.uId != null, true);
    expect(result?.mId != null, true);
  });
}
