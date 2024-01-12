import 'package:my_tutorial_app/net/getMessages.dart';
import 'package:test/test.dart';

void main() {
  test('should return a list of valid messages', () async {
    final result = await getMessages();
    int length = result.length;
    // testing that all Messages recieved have no null fields
    for(int i=0; i<length; i++){
      expect(result[i].id != null, true);
      expect(result[i].subject != null, true);
      expect(result[i].message != null, true);
      expect(result[i].upvotes != null, true);
      expect(result[i].downvotes != null, true);
    }
  });
}
