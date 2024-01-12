class Upvote{
  int? uvId;
  int? mId;
  int? uId;

  //default constructor
  Upvote({int? uvId, int? mId, int? uId}){
    if (uvId != null) { 
      this.uvId = uvId; 
    }
    if (mId != null) { 
      this.mId = mId; 
    }
    if (uId != null) { 
      this.uId = uId;
    }
  }
  //This constructor also does the work of initialising the variables
  Upvote.fromJson(Map<String,dynamic> json){
    uvId = json['uvId'];
    uId = json['mId'];
    mId = json['uId'];
  }
}
// convert a list of jsons into messages
List<Upvote> fromJsons(List<dynamic> json){
  List<Upvote> result = List<Upvote>.filled(json.length, Upvote(), growable: false);
  for(int i=0; i < json.length; i++){
    result[i] = Upvote.fromJson(json[i]);
  }
  return result;
}