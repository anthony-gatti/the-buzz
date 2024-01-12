class Downvote{
  int? dvId;
  int? mId;
  int? uId;

  //default constructor
  Downvote({int? dvId, int? mId, int? uId}){
    if (dvId != null) { 
      this.dvId = dvId; 
    }
    if (mId != null) { 
      this.mId = mId; 
    }
    if (uId != null) { 
      this.uId = uId;
    }
  }
  //This constructor also does the work of initialising the variables
  Downvote.fromJson(Map<String,dynamic> json){
    dvId = json['dvId'];
    uId = json['mId'];
    mId = json['uId'];
  }
}
// convert a list of jsons into messages
List<Downvote> fromJsons(List<dynamic> json){
  List<Downvote> result = List<Downvote>.filled(json.length, Downvote(), growable: false);
  for(int i=0; i < json.length; i++){
    result[i] = Downvote.fromJson(json[i]);
  }
  return result;
}