import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter/services.dart';
import 'package:like_button/like_button.dart';
import 'package:my_tutorial_app/models/Comment.dart';
import 'package:my_tutorial_app/net/getComments.dart';
import 'package:my_tutorial_app/models/Message.dart';
import 'package:my_tutorial_app/net/getMessages.dart';
import 'dart:io';
import 'package:image_picker/image_picker.dart';

import 'package:my_tutorial_app/net/getMessage.dart';
import 'package:my_tutorial_app/net/postMessage.dart';
import 'package:my_tutorial_app/net/postComment.dart';
import 'package:my_tutorial_app/net/postUpvote.dart';
import 'package:my_tutorial_app/net/postDownvote.dart';
import 'package:my_tutorial_app/net/deleteUpvote.dart';
import 'package:my_tutorial_app/net/deleteDownvote.dart';
import 'package:my_tutorial_app/net/getUpvote.dart';
import 'package:my_tutorial_app/net/getDownvote.dart';
import 'package:my_tutorial_app/net/putComment.dart';

import 'package:my_tutorial_app/main.dart';
import 'package:my_tutorial_app/pages/home_page.dart';
import 'package:my_tutorial_app/pages/create_post_page.dart';
import 'package:my_tutorial_app/pages/profile_page.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class CommentSection extends StatefulWidget {
  const CommentSection({required this.mId, required this.uId});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final int mId;
  final int uId;

  @override
  State<CommentSection> createState() => _CommentSectionState();
}

class _CommentSectionState extends State<CommentSection> {

  @override
  Widget build(BuildContext context) {
    final myController1 = TextEditingController();
    final myController2 = TextEditingController();
    int mId = widget.mId;
    int uId = widget.uId;
    File? selectedFile;
    String? url;
    String attachmentButton = "Upload attachment";

      Future<void> getPicture(bool openCamera) async {
        XFile? pickedFile;
        
        if (openCamera) {
          pickedFile = await ImagePicker().pickImage(source: ImageSource.camera);
        } else {
          pickedFile = await ImagePicker().pickImage(source: ImageSource.gallery);
        }

        if (pickedFile != null) {
          print('Picked picture: ${pickedFile.path}');
          setState(() {
            selectedFile = File(pickedFile?.path ?? '');
            attachmentButton = openCamera ? 'Camera Picture' : 'Gallery Picture';
          });
        }
  }

    // after a post is posted, this is run
    @override
    void dispose() {
      // Clean up the controller when the widget is disposed.
      myController1.dispose();
      super.dispose();
    }

    return Scaffold(
      appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text('Comment Section'),
        // This is a menu to appear in the app bar to access other pages
      ),
      body: Center(
          child: HttpReqWords(mId: mId, uId: uId),
      ),
      // code for posting a post     
      floatingActionButton: FloatingActionButton(
        // When the user presses the button, show an alert dialog containing
        // the text that the user has entered into the text field.
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                title: const Text('Post a Comment'),
                content: const SingleChildScrollView( child: ListBody( children: <Widget>[],),),
                actions: <Widget>[
                  IconButton(
                    icon: const Icon(Icons.cancel),
                    tooltip: 'X',
                    onPressed: () {
                      Navigator.pop(context);
                    },
                  ),
                  TextField(
                    controller: myController1,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      hintText: 'Content',
                    ),
                  ),
                  TextField(
                    controller: myController2,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      hintText: 'url',
                    ),
                  ),
                  IconButton(
                      icon: Text(attachmentButton) , 
                      onPressed: () async {
                      getPicture(true);
                  } 
                  ),
                  IconButton(
                    icon: const Text("Post"),
                    tooltip: 'plus',
                    onPressed: () {
                      postComment(mId, myController1.text, myController2.text, selectedFile);
                      //dispose();
                      Navigator.pop(context);
                    },
                  ),
                ],
              );
            },
          );
        },
        tooltip: 'subject',
        child: const Icon(Icons.add),
      ),
    );
  }
}

class HttpReqWords extends StatefulWidget {
  final int mId;
  final int uId;
  const HttpReqWords({super.key, required this.mId, required this.uId});

  @override
  State<HttpReqWords> createState() => _HttpReqWordsState();
}

class _HttpReqWordsState extends State<HttpReqWords> {
  // final _words = <String>[]; // also try: var _words = <String>['a', 'b', 'c', 'd'];
  // var _words = <String>['a', 'b', 'c', 'd'];
  final _biggerFont = const TextStyle(fontSize: 18);
  final _mediumFont = const TextStyle(fontSize: 14);
  late Future<List<Comment>> _future_comments;
  late Future<Message> _future_message;
  //late Future<List<NumberWordPair>> _future_list_numword_pairs;

  @override
  void initState() {
    super.initState();
    int mId = widget.mId;
    _future_message = getMessage(mId);
    _future_comments = getComments(mId);
    //_future_list_numword_pairs = fetchNumberWordPairs();
  }

  Future<List<Comment>> _retry() async {
    setState(() {
      int mId = widget.mId;
      _future_message = getMessage(mId);
      _future_comments = getComments(mId);
    });
    return _future_comments;
  }

  @override
  Widget build(BuildContext context) {
    return build_v4(context);
  }
  // code for the list of Messages
  Widget build_v4(BuildContext context) {
    final myController1 = TextEditingController();
    final myController2 = TextEditingController();
    int mId = widget.mId;
    int uId = widget.uId;
    File? selectedFile;
    Color upvoteColor = Colors.grey;
    Color downvoteColor = Colors.grey;

    // after a post is posted, this is run
    @override
    void dispose() {
      // Clean up the controller when the widget is disposed.
      myController1.dispose();
      super.dispose();
    }

    //Makes the upvote blue if the user has already upvoted
    void setUpvoteColor(int isUpvoted){
      if(isUpvoted == 1){
        upvoteColor = Colors.blue;
      }
    }
    
    //Makes the upvote blue if the user has already upvoted
    void setDownvoteColor(int isDownvoted){
      if(isDownvoted == 1){
        downvoteColor = Colors.blue;
      }
    }

    // potential solution to list loading issue
    final ScrollController sController = ScrollController();

    // code for like of Message items with like buttons
    var fb = FutureBuilder<List<Comment>>(
      future: _future_comments, // Use only one future here
      builder: (BuildContext commentContext, AsyncSnapshot<List<Comment>> snapshotComments) {

        return FutureBuilder<Message>(
          future: _future_message, // Use the second future here
          builder: (BuildContext messageContext, AsyncSnapshot<Message> snapshotMessage) {
            Widget child;
            if (snapshotComments.hasData && snapshotMessage.hasData) {
              //Calls to check if the user has already upvoted/downvoted a specific post
              setUpvoteColor(snapshotMessage.data!.isUpvote!);
              setDownvoteColor(snapshotMessage.data!.isDownvote!);
              child = RefreshIndicator(
                onRefresh: _retry,
                child: Column(
                  children: <Widget>[
                    Text(
                      "${snapshotMessage.data!.subject}",
                      // snapshot.data![i].str,
                      style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                      textAlign: TextAlign.left,
                    ),
                    Text(
                      "${snapshotMessage.data!.message}",
                      // snapshot.data![i].str,
                      style: const TextStyle(fontSize: 15),
                      textAlign: TextAlign.center,
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.end, // This will evenly space the widgets
                      children: <Widget>[
                        // Upvote Button, Downvote Button, and Counters
                        Row(
                          children: <Widget>[
                            // Upvote Counter
                            Text(
                              "${snapshotMessage.data!.upvotes}",
                              style: TextStyle(fontSize: 15),
                              textAlign: TextAlign.center,
                            ),
                            // Upvote Button
                            InkWell(
                              onTap: () {
                                setState(() {
                                  if(upvoteColor == Colors.grey){
                                    upvoteColor == Colors.blue;
                                  } else {
                                    upvoteColor = Colors.grey;
                                  }
                                  int mId = snapshotMessage.data!.id!;
                                  if(upvoteColor == Colors.grey){
                                    postUpvote(mId);
                                  }else{
                                    deleteUpvote(mId);
                                  }
                                });
                              },
                              child: Icon(
                                Icons.thumb_up,
                                size: 26,
                                color: upvoteColor,
                              ),
                            ),
                            // Downvote Button
                            InkWell(
                              onTap: () {
                                setState(() {
                                  if(downvoteColor == Colors.grey){
                                    downvoteColor == Colors.blue;
                                  } else {
                                    downvoteColor = Colors.grey;
                                  }
                                  int mId = snapshotMessage.data!.id!;
                                  if(downvoteColor == Colors.grey){
                                    postDownvote(mId);
                                  }else{
                                    deleteDownvote(mId);
                                  }
                                });
                              },
                              child: Icon(
                                Icons.thumb_down,
                                size: 26,
                                color: downvoteColor,
                              ),
                            ),
                            // Downvote Count
                            Text(
                              "${snapshotMessage.data!.downvotes}",
                              style: TextStyle(fontSize: 15),
                              textAlign: TextAlign.right,
                            ),
                          ],
                        ),
                      ],
                    ),
                    Divider(height: 1.0),
                    Expanded(child: ListView.builder(
                      //shrinkWrap: true, //expensive! consider refactoring. https://api.flutter.dev/flutter/widgets/ScrollView/shrinkWrap.html
                      padding: const EdgeInsets.all(16.0),
                      itemCount: snapshotComments.data!.length,
                      // new posts appear at the top
                      reverse: true,
                      controller: sController,
                      itemBuilder: (commentContext, i) {
                        return Column(
                          children: <Widget>[
                            // subject
                            ListTile(
                              title: InkWell(
                                onTap:() async {
                                  SharedPreferences prefs = await SharedPreferences.getInstance();
                                  final int? curr_user = prefs.getInt('uId');  // send request
                                  Navigator.push(context, MaterialPageRoute(builder: (context) => Profile(uId: snapshotComments.data![i].uId!, curr_user: curr_user!,)));
                                },
                                child: Text(
                                  "User: ${snapshotComments.data![i].uId}",
                                  // snapshot.data![i].str,
                                  style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                                  textAlign: TextAlign.left,
                                ),
                              )
                            ),
                            ListBody(
                              children: <Widget>[
                                // message
                                Column(children: [
                                  Text(
                                    "${snapshotComments.data![i].content}",
                                    style: const TextStyle(fontSize: 18),
                                    textAlign: TextAlign.left,
                                  ),
                                  // Text(
                                  //   "${snapshotComments.data![i].url}",
                                  //   style: const TextStyle(fontSize: 18),
                                  //   textAlign: TextAlign.left,
                                  // ),
                          
                                  Visibility(
                                    visible: uId == snapshotComments.data![i].uId ? true : false,
                                    child: Column(
                                      mainAxisAlignment: MainAxisAlignment.center,
                                      mainAxisSize: MainAxisSize.max,
                                      children: [
                                        ElevatedButton(
                                          child: Text(
                                            "Edit"
                                          ),
                                          onPressed: () {
                                            showDialog(
                                              context: context,
                                              builder: (context) {
                                                return AlertDialog(
                                                  title: const Text('Update a Comment'),
                                                  content: const SingleChildScrollView( child: ListBody( children: <Widget>[],),),
                                                  actions: <Widget>[
                                                    IconButton(
                                                      icon: const Icon(Icons.cancel),
                                                      tooltip: 'X',
                                                      onPressed: () {
                                                        Navigator.pop(context);
                                                      },
                                                    ),
                                                    TextField(
                                                      controller: myController1,
                                                      decoration: const InputDecoration(
                                                        border: OutlineInputBorder(),
                                                        hintText: 'Content',
                                                      ),
                                                    ),
                                                    TextField(
                                                        controller: myController2,
                                                        decoration: const InputDecoration(
                                                          border: OutlineInputBorder(),
                                                          hintText: 'url',
                                                        ),
                                                      ),
                                                      // IconButton(
                                                      //     icon: Text(attachmentButton) , 
                                                      //     onPressed: () {
                                                      //     getPicture(true);
                                                      // } 
                                                      //),
                                                    IconButton(
                                                      icon: const Text("Edit"),
                                                      tooltip: 'plus',
                                                      onPressed: () {
                                                    
                                                        putComment(snapshotComments.data![i].cId!, myController1.text);
                                                        //dispose();
                                                        Navigator.pop(context);
                                                      },
                                                    ),
                                                  ],
                                                );
                                              },
                                            );
                                          },
                                        )
                                      ],
                                    ),
                                  ),
                                ],)
                              ],
                            ),
                            Divider(height: 1.0),
                          ],
                        );
                      })
                    ),
                  ],
                ),
              );
            } else if (snapshotComments.hasError) { // newly added
              child = Text('${snapshotComments.error}');
            } else {
              child = const CircularProgressIndicator(); //show a loading spinner.
            }
            return child;
          },
        );
      },
    );
    return fb;
  }  
}