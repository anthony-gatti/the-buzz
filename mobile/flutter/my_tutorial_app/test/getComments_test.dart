import 'package:my_tutorial_app/net/getComments.dart';
import 'package:test/test.dart';

void main() {
  test('should return a list of valid comments', () async {
    final result = await getComments(1);
    int length = result.length;
    // testing that all Messages recieved have no null fields
    for(int i=0; i<length; i++){
      expect(result[i].cId != null, true);
      expect(result[i].content != null, true);
      expect(result[i].uId != null, true);
      expect(result[i].mId != null, true);
    }
  });
}
