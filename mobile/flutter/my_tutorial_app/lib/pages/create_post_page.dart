import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter/services.dart';
import 'package:image_picker/image_picker.dart';
import 'package:like_button/like_button.dart';
import 'package:my_tutorial_app/models/Message.dart';
import 'package:my_tutorial_app/net/getMessages.dart';
import 'dart:io';


import 'package:my_tutorial_app/net/postMessage.dart';

import 'package:my_tutorial_app/main.dart';
import 'package:my_tutorial_app/pages/home_page.dart';
import 'package:my_tutorial_app/pages/profile_page.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:file_picker/file_picker.dart';

class CreatePost extends StatefulWidget {
  const CreatePost({required this.uId});

  final int uId;

  @override
  _CreatePostState createState() => _CreatePostState();
}

// Page to create a new message
class _CreatePostState extends State<CreatePost> {
  final TextEditingController myController1 = TextEditingController();
  final TextEditingController myController2 = TextEditingController();
  final TextEditingController myController3 = TextEditingController();
  File? selectedFile;
  String attachmentButton = 'Upload Attachment';

  // Future getImage(bool openCamera) async{
  //   XFile? pickedFile;
  //   if(openCamera){
  //     pickedFile = await ImagePicker.pickImage(source: ImageSource.camera);
  //     setState(() {
  //       selectedFile = (File?) pickedFile;
  //     });
  //   }
  // }

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
  void dispose() {
    myController1.dispose();
    myController2.dispose();
    myController3.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    int uId = widget.uId;
    return Scaffold(
      appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text('Create a Post'),
        // This is a menu to appear in the app bar to access other pages
        actions: [
          PopupMenuButton<String>(
            onSelected: (value) async {
              SharedPreferences prefs = await SharedPreferences.getInstance();
              final int? curr_user = prefs.getInt('uId');  // send request
              // Handle menu item selection
              if (value == 'Home') {
                Navigator.push(context, MaterialPageRoute(builder: (context) => MyHomePage(title: 'The Buzz', uId: uId)));
              } else if (value == 'Profile') {
                Navigator.push(context, MaterialPageRoute(builder: (context) => Profile(uId: uId, curr_user: curr_user!,)));
              }
            },
            itemBuilder: (BuildContext context) {
              return <PopupMenuEntry<String>>[
                PopupMenuItem<String>(
                  value: 'Home',
                  child: Text('Home'),
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
      body: Padding( // Form for a new post
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
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
            TextField(
              controller: myController3,
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                hintText: 'url',
              ),
            ),
            SizedBox(height: 16.0),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                ElevatedButton(
                    onPressed: () async {
                        getPicture(true);
                    },
                    child: Text(attachmentButton),
                  ),
                ElevatedButton(
                  onPressed: () {
                      if (selectedFile != null) {
                      postMessage(myController1.text, myController2.text, myController3.text, selectedFile);
                      Navigator.pop(context);
                    } else {
                      selectedFile = null;
                      postMessage(myController1.text, myController2.text, myController3.text, selectedFile);
                      Navigator.pop(context);
                    }
                  },
                  child: Text('Post'),
                ),
                
              ],
            ),
          ],
        ),
      ),
    );
  }
}