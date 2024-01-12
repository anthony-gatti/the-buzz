class Message{
  int? id;
  String? subject;
  String? message;
  int? userId;
  int? upvotes;
  int? downvotes;
  int? isUpvote;
  int? isDownvote;
  String? created;

  //default constructor
  Message({int? id, String? subject, String? message, int? userId, int? upvotes, int? downvotes, int? isUpvote, int? isDownvote, String? created}){
    if (id != null) { 
      this.id = id; 
    }
    if (subject != null) { 
      this.subject = subject; 
    }
    if (message != null) { 
      this.message = message; 
    }
    if (userId != null) { 
      this.userId = userId; 
    }
    if (upvotes != null) { 
      this.upvotes = upvotes;
    }
    if (downvotes != null) { 
      this.downvotes = downvotes;
    }
    if (isUpvote != null) { 
      this.isUpvote = isUpvote;
    }
    if (isDownvote != null) { 
      this.isDownvote = isDownvote;
    }
    if (created != null) { 
      this.created = created;
    }
  }
  //This constructor also does the work of initialising the variables
  Message.fromJson(Map<String,dynamic> json){
    id = json['mid'];
    subject = json['mSubject'];
    message = json['mMessage'];
    userId = json['uid'];
    upvotes = json['mUpvotes'];
    downvotes = json['mDownvotes'];
    isUpvote = json['mIsUpvote'];
    isDownvote = json['mIsDownvote'];
    created = json['mCreated'];
  }
}
// convert a list of jsons into messages
List<Message> fromJsons(List<dynamic> json){
  List<Message> result = List<Message>.filled(json.length, Message(), growable: false);
  for(int i=0; i < json.length; i++){
    result[i] = Message.fromJson(json[i]);
  }
  return result;
}