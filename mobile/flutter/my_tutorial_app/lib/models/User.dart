class User{
  int? id;
  String? name;
  String? email;
  String? username;
  String? gender;
  String? sexualOrientation;
  String? note;

  //default constructor
  User({int? id, String? name, String? email, String? username, String? gender, String? sexualOrientation, String? note}){
    if (id != null) { 
      this.id = id; 
    }
    if (name != null) { 
      this.name = name; 
    }
    if (email != null) { 
      this.email = email; 
    }
    if (username != null) { 
      this.username = username;
    }
    if (gender != null) { 
      this.gender = gender;
    }
    if (sexualOrientation != null) { 
      this.sexualOrientation = sexualOrientation;
    }
    if (note != null) { 
      this.note = note;
    }
  }
  //This constructor also does the work of initialising the variables
  User.fromJson(Map<String,dynamic> json){
    id = json['uId'];
    name = json['uName'];
    email = json['uEmail'];
    username = json['uUsername'];
    gender = json['uGender'];
    sexualOrientation = json['uSO'];
    note = json['mNote'];
  }
}
// convert a list of jsons into messages
List<User> fromJsons(List<dynamic> json){
  List<User> result = List<User>.filled(json.length, User(), growable: false);
  for(int i=0; i < json.length; i++){
    result[i] = User.fromJson(json[i]);
  }
  return result;
}