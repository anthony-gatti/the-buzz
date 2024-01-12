import 'package:my_tutorial_app/net/getUser.dart';
import 'package:test/test.dart';

void main() {
  test('should return a valid user', () async {
    final result = await getUser(1);
    // testing that the Message recieved has no null fields
    expect(result.id != null, true);
    expect(result.name != null, true);
    expect(result.email != null, true);
    expect(result.username != null, true);
    expect(result.gender != null, true);
    expect(result.sexualOrientation != null, true);
    expect(result.note != null, true);
  });
}
