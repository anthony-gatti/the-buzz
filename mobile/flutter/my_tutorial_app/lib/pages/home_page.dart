//import 'dart:js_util';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter/services.dart';
import 'package:like_button/like_button.dart';
import 'package:my_tutorial_app/models/Message.dart';
import 'package:my_tutorial_app/models/Upvote.dart';
import 'package:my_tutorial_app/models/Downvote.dart';
import 'package:my_tutorial_app/net/getMessages.dart';
import 'dart:io';
import 'package:file_picker/file_picker.dart';
import 'package:permission_handler/permission_handler.dart';
// import 'package:simple_permissions/simple_permissions.dart';
import 'package:image_picker/image_picker.dart';

import 'package:my_tutorial_app/net/postMessage.dart';
import 'package:my_tutorial_app/net/postUpvote.dart';
import 'package:my_tutorial_app/net/postDownvote.dart';
import 'package:my_tutorial_app/net/getComments.dart';
import 'package:my_tutorial_app/net/getUpvote.dart';
import 'package:my_tutorial_app/net/getDownvote.dart';
import 'package:my_tutorial_app/net/deleteUpvote.dart';
import 'package:my_tutorial_app/net/deleteDownvote.dart';

import 'package:my_tutorial_app/models/Comment.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart' as http;

import 'package:my_tutorial_app/main.dart';
import 'package:my_tutorial_app/pages/create_post_page.dart';
import 'package:my_tutorial_app/pages/profile_page.dart';
import 'package:my_tutorial_app/pages/comment_section_page.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title, required this.uId});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;
  final int uId;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

// code for top App Bar and Post button
class _MyHomePageState extends State<MyHomePage> {
  
  @override
  Widget build(BuildContext context) {
    final myController1 = TextEditingController();
    final myController2 = TextEditingController();
    int uId = widget.uId;
    File? selectedFile;
    String attachmentButton = 'Upload Attachment';

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


    @override
    void initState() {
      super.initState();
      
      
    }
    // after a post is posted, this is run
    @override
    void dispose() {
      // Clean up the controller when the widget is disposed.
      myController1.dispose();
      myController2.dispose();
      super.dispose();
    }

    void showPermissionDeniedDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Permission Required'),
          content: Text('To access files, the storage permission is required.'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
                // Open app settings to allow the user to grant permission manually
                openAppSettings();
              },
              child: Text('Open Settings'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
                // Handle other actions or show additional messages
              },
              child: Text('Cancel'),
            ),
          ],
        );
      },
    );
  }

    Future<void> requestPermissions() async {
      Map<Permission, PermissionStatus> status = await [
        Permission.camera,
        Permission.storage,
      ].request();

      if (status[Permission.camera] == PermissionStatus.denied) {
        print("Camera permission denied");
        showPermissionDeniedDialog();
      }

      if (status[Permission.storage] == PermissionStatus.denied) {
        print("Storage permission denied");
        showPermissionDeniedDialog();
      }
}


    
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
        // This is a menu to appear in the app bar to access other pages
        actions: [
          //Menu to navigate to other pages
          PopupMenuButton<String>(
            onSelected: (value) async {
              SharedPreferences prefs = await SharedPreferences.getInstance();
              final int? curr_user = prefs.getInt('uId');  // send request
              // Handle menu item selection
              if (value == 'Create Post') {
                Navigator.push(context, MaterialPageRoute(builder: (context) => CreatePost(uId: uId)));
              } else if (value == 'Profile') {
                Navigator.push(context, MaterialPageRoute(builder: (context) => Profile(uId: uId, curr_user: curr_user!,)));
              } 
            },
            itemBuilder: (BuildContext context) {
              return <PopupMenuEntry<String>>[
                PopupMenuItem<String>(
                  value: 'Create Post',
                  child: Text('Create Post'),
                ),
                PopupMenuItem<String>(
                  value: 'Profile',
                  child: Text('Profile'),
                ),
              ];
            },
          ),
        ]
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: HttpReqWords(uId: uId),
      ),// This trailing comma makes auto-formatting nicer for build methods. 
      // code for posting a post     
      floatingActionButton: FloatingActionButton(
        // When the user presses the button, show an alert dialog containing
        // the fields to create a new post.
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) {
              return AlertDialog(
                title: const Text('Post to Buzz'),
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
                      hintText: 'Subject',
                    ),
                  ),
                  TextField(
                    controller: myController2,
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      hintText: 'Message',
                    ),
                  ),
                  IconButton(
                    onPressed: () async {

                  
                      // requestPermissions();

                      // Check if permission is granted before picking a file
                      // if (await Permission.storage.isGranted) {
                      //     // Open file picker
                      //   FilePickerResult? result = await FilePicker.platform.pickFiles();

                      //   if (result != null) {
                      //     // Handle the picked file
                      //     PlatformFile file = result.files.first;
                      //     print('Picked file: ${file.name}');
                      //     setState(() {
                      //       // Update the selected file variable
                      //       selectedFile = File(file.path ?? '');
                      //       // Change the button text to the file name
                      //       attachmentButton= file.name;
                      //     });
                      //   } else {
                      //     // exit
                      //   }
                      // }else{
                      //   print('Permission Denied');
                      // }

                          getPicture(true);
                      
                    },
                    icon: Text(attachmentButton), 
                  ),
                  IconButton(
                    icon: const Text("Post"),
                    tooltip: 'plus',
                    onPressed: () {
                      if (selectedFile != null) {
                          // Use the selected file when posting
                          postMessage(myController1.text, myController2.text, null, selectedFile);
                          Navigator.pop(context); 
                          }else {
                          selectedFile = null;
                          postMessage(myController1.text, myController2.text, null, selectedFile);
                          Navigator.pop(context);
                    }
                  }
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
  const HttpReqWords({super.key, required this.uId});

  final int uId;

  @override
  State<HttpReqWords> createState() => _HttpReqWordsState();
}

class _HttpReqWordsState extends State<HttpReqWords> {
  // final _words = <String>[]; // also try: var _words = <String>['a', 'b', 'c', 'd'];
  // var _words = <String>['a', 'b', 'c', 'd'];
  final _biggerFont = const TextStyle(fontSize: 18);
  final _mediumFont = const TextStyle(fontSize: 14);
  late Future<List<Message>> _future_posts;
  //late Future<List<NumberWordPair>> _future_list_numword_pairs;

  @override
  void initState() {
    super.initState();
    _future_posts = getMessages();
    //_future_list_numword_pairs = fetchNumberWordPairs();
  }

  Future<List<Message>> _retry() async {
    setState(() {
      _future_posts = getMessages();
    });
    return _future_posts;
  }

  @override
  Widget build(BuildContext context) {
    return build_v4(context);
  }
  // code for the list of Messages
  Widget build_v4(BuildContext context) {
    int uId = widget.uId;
    Color upvoteColor = Colors.grey;
    Color downvoteColor = Colors.grey;

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
    var fb = FutureBuilder<List<Message>>(
      future: _future_posts,
      builder: (BuildContext context, AsyncSnapshot<List<Message>> snapshot) {
        Widget child;

        if (snapshot.hasData) {

          // developer.log('`using` ${snapshot.data}', name: 'my.app.category');
          // create  listview to show one row per array element of json response
          child = RefreshIndicator(onRefresh: _retry, child: ListView.builder(
              //shrinkWrap: true, //expensive! consider refactoring. https://api.flutter.dev/flutter/widgets/ScrollView/shrinkWrap.html
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.length,
              // new posts appear at the top
              reverse: true,
              controller: sController,
              itemBuilder: (context, i) {
                  //Calls to check if the user has already upvoted/downvoted a specific post
                  setUpvoteColor(snapshot.data![i].isUpvote!);
                  setDownvoteColor(snapshot.data![i].isDownvote!);
                return Column(
                  children: <Widget>[
                    // subject
                    ListTile(
                      title: Text(
                        "${snapshot.data![i].subject}",
                        style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                        textAlign: TextAlign.left,
                      ),
                    ),
                    ListBody(
                      children: <Widget>[
                        // message
                        Text(
                          "${snapshot.data![i].message}",
                          style: const TextStyle(fontSize: 15),
                          textAlign: TextAlign.center,
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween, // This will evenly space the widgets
                          children: <Widget>[
                            // Left side - Comment Button and Comment Counter
                            Row(
                              children: <Widget>[
                                // Comment Button
                                IconButton(
                                  iconSize: 26,
                                  icon: const Icon(Icons.comment),
                                  onPressed: () { // send request
                                    int mId = snapshot.data![i].id!;
                                    Navigator.push(context, MaterialPageRoute(builder: (context) => CommentSection(mId: mId, uId: uId)));
                                  },
                                ),
                              ],
                            ),
                            // Right side - Upvote Button, Downvote Button, and Counters
                            Row(
                              children: <Widget>[
                                // Upvote Counter
                                Text(
                                  "${snapshot.data![i].upvotes}",
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
                                      int mId = snapshot.data![i].id!;
                                      if(snapshot.data![i].isUpvote == 0){
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
                                      int mId = snapshot.data![i].id!;
                                      if(snapshot.data![i].isDownvote == 0){
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
                                  "${snapshot.data![i].downvotes}",
                                  style: TextStyle(fontSize: 15),
                                  textAlign: TextAlign.center,
                                ),
                              ],
                            ),
                          ],
                        )
                      ],
                    ),
                    Divider(height: 1.0),
                  ],
                );
              })
          );
        } else if (snapshot.hasError) { // newly added
          child = Text('${snapshot.error}');
        } else {
          child = const CircularProgressIndicator(); //show a loading spinner.
        }
        return child;
      },
    );

    return fb;
  }
}