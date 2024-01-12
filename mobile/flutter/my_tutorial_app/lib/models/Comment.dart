class Comment{
  int? cId;
  String? content;
  int? uId;
  int? mId;
  String? mCreated;

  //default constructor
  Comment({int? cId, String? content, int? uId, int? mId, String? mCreated}){
    if (cId != null) { 
      this.cId = cId; 
    }
    if (content != null) { 
      this.content = content; 
    }
    if (uId != null) { 
      this.uId = uId; 
    }
    if (mId != null) { 
      this.mId = mId;
    }
    if (mCreated != null) { 
      this.mCreated = mCreated;
    }
  }
  //This constructor also does the work of initialising the variables
  Comment.fromJson(Map<String,dynamic> json){
    cId = json['cId'];
    content = json['cContent'];
    uId = json['uId'];
    mId = json['mId'];
    mCreated = json['mCreated'];
  }
}
// convert a list of jsons into messages
List<Comment> fromJsons(List<dynamic> json){
  List<Comment> result = List<Comment>.filled(json.length, Comment(), growable: false);
  for(int i=0; i < json.length; i++){
    result[i] = Comment.fromJson(json[i]);
  }
  return result;
}