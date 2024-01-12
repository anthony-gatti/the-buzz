import 'package:my_tutorial_app/net/getMessage.dart';
import 'package:test/test.dart';

void main() {
  test('should return a valid message', () async {
    final result = await getMessage(1);
    // testing that the Message recieved has no null fields
    expect(result.id != null, true);
    expect(result.subject != null, true);
    expect(result.message != null, true);
    expect(result.upvotes != null, true);
    expect(result.downvotes != null, true);
  });
}
