import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter/services.dart';
import 'package:like_button/like_button.dart';
import 'package:my_tutorial_app/models/Message.dart';
import 'package:my_tutorial_app/models/User.dart';
import 'package:my_tutorial_app/net/getMessages.dart';
import 'package:my_tutorial_app/net/getUser.dart';
import 'package:my_tutorial_app/net/putName.dart';
import 'package:my_tutorial_app/net/putEmail.dart';
import 'package:my_tutorial_app/net/putUsername.dart';
import 'package:my_tutorial_app/net/putGender.dart';
import 'package:my_tutorial_app/net/putSO.dart';
import 'package:my_tutorial_app/net/putNote.dart';

import 'dart:io';

import 'package:my_tutorial_app/net/postMessage.dart';

import 'package:my_tutorial_app/pages/create_post_page.dart';
import 'package:my_tutorial_app/pages/home_page.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Profile extends StatefulWidget {
  const Profile({required this.uId, required this.curr_user});

  final int uId;
  final int curr_user;

  @override
  State<Profile> createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {
  @override
  Widget build(BuildContext context) {
    int uId = widget.uId;
    int curr_user = widget.curr_user;
    return Scaffold(
      appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text('Profile'),
        // This is a menu to appear in the app bar to access other pages
        actions: [
          PopupMenuButton<String>(
            onSelected: (value) {
              // Handle menu item selection
              if (value == 'Home') {
                Navigator.push(context, MaterialPageRoute(builder: (context) => MyHomePage(title: 'The Buzz', uId: uId)));
              } else if (value == 'Create Post') {
                Navigator.push(context, MaterialPageRoute(builder: (context) => CreatePost(uId: uId)));
              }
            },
            itemBuilder: (BuildContext context) {
              return <PopupMenuEntry<String>>[
                PopupMenuItem<String>(
                  value: 'Home',
                  child: Text('Home'),
                ),
                PopupMenuItem<String>(
                  value: 'Create Post',
                  child: Text('Create a Post'),
                ),
              ];
            },
          ),
        ]
      ),
      body: Center(
        child: Align(alignment: Alignment.centerLeft,
          child: Padding(
            padding: const EdgeInsets.all(16),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                ProfileInfo(uId: uId, curr_user: curr_user,),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class ProfileInfo extends StatefulWidget {
  final int uId;
  final int curr_user;
  const ProfileInfo({super.key, required this.uId, required this.curr_user});

  @override
  State<ProfileInfo> createState() => _ProfileInfoState();
}

class _ProfileInfoState extends State<ProfileInfo> {
  // final _words = <String>[]; // also try: var _words = <String>['a', 'b', 'c', 'd'];
  // var _words = <String>['a', 'b', 'c', 'd'];
  final _biggerFont = const TextStyle(fontSize: 18);
  final _mediumFont = const TextStyle(fontSize: 14);
  late Future<User> _future_user;
  
  //late Future<List<NumberWordPair>> _future_list_numword_pairs;

  @override
  void initState() {
    super.initState();
    int uId = widget.uId;
    _future_user = getUser(uId);
    //_future_list_numword_pairs = fetchNumberWordPairs();
  }

  Future<User> _retry() async {
    setState(() {
      int uId = widget.uId;
      _future_user = getUser(uId);
    });
    return _future_user;
  }

  @override
  Widget build(BuildContext context) {
    return build_v4(context);
  }
  // code for the list of Messages
  Widget build_v4(BuildContext context) {
    int uId = widget.uId;
    int curr_user = widget.curr_user;

    // code for like of Message items with like buttons
    var fb = FutureBuilder<User>(
      future: _future_user,
      builder: (BuildContext context, AsyncSnapshot<User> snapshot) {
        final myController1 = TextEditingController();
        final myController2 = TextEditingController();
        final myController3 = TextEditingController();
        final myController4 = TextEditingController();
        final myController5 = TextEditingController();
        final myController6 = TextEditingController();
        Widget child;

        if (snapshot.hasData) {
          return RefreshIndicator(
            onRefresh: _retry,
            child: SingleChildScrollView( 
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center, 
                mainAxisSize: MainAxisSize.max, 
                children: [
                  Text(
                    'Personal Information',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 24, 
                      fontWeight: FontWeight.bold, 
                    ),
                  ),
                  //Name
                  Text(
                    '${snapshot.data!.name}',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 18, 
                    ),
                  ),
                  Visibility(
                    visible: uId == curr_user ? true : false,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      children: [
                        //Edit name button
                        SizedBox(
                          width: 105,
                          height: 30, 
                          child: ElevatedButton(
                            child: Text(
                              "Edit Name",
                              style: TextStyle(
                                fontSize: 12
                              ),
                            ),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    title: const Text('Update Name'),
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
                                          hintText: 'Name',
                                        ),
                                      ),
                                      IconButton(
                                        icon: const Text("Edit"),
                                        tooltip: 'plus',
                                        onPressed: () async {
                                          SharedPreferences prefs = await SharedPreferences.getInstance();
                                          final int? uId = prefs.getInt('uId');  // send request
                                          putName(uId!, myController1.text);
                                          //dispose();
                                          Navigator.pop(context);
                                        },
                                      ),
                                    ],
                                  );
                                },
                              );
                            },
                          ),
                        ),
                      ],
                    ),
                  ),
                  //Username
                  Text(
                    '${snapshot.data!.username}',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 18, 
                    ),
                  ),
                  Visibility(
                    visible: uId == curr_user ? true : false,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      children: [
                        //Edit username button
                        SizedBox(
                          width: 130,
                          height: 30, 
                          child: ElevatedButton(
                            child: Text(
                              "Edit Username",
                              style: TextStyle(
                                fontSize: 12
                              ),
                            ),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    title: const Text('Update Username'),
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
                                        controller: myController2,
                                        decoration: const InputDecoration(
                                          border: OutlineInputBorder(),
                                          hintText: 'Username',
                                        ),
                                      ),
                                      IconButton(
                                        icon: const Text("Edit"),
                                        tooltip: 'plus',
                                        onPressed: () async {
                                          SharedPreferences prefs = await SharedPreferences.getInstance();
                                          final int? uId = prefs.getInt('uId');  // send request
                                          putUsername(uId!, myController2.text);
                                          //dispose();
                                          Navigator.pop(context);
                                        },
                                      ),
                                    ],
                                  );
                                },
                              );
                            },
                          ),
                        ),
                      ],
                    ),
                  ),
                  //Email
                  Text(
                    '${snapshot.data!.email}',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 18,
                    ),
                  ),
                  Visibility(
                    visible: uId == curr_user ? true : false,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      children: [
                        //Edit email button
                        SizedBox(
                          width: 105,
                          height: 30, 
                          child: ElevatedButton(
                            child: Text(
                              "Edit Email",
                              style: TextStyle(
                                fontSize: 12
                              ),
                            ),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    title: const Text('Update Email'),
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
                                        controller: myController3,
                                        decoration: const InputDecoration(
                                          border: OutlineInputBorder(),
                                          hintText: 'Email',
                                        ),
                                      ),
                                      IconButton(
                                        icon: const Text("Edit"),
                                        tooltip: 'plus',
                                        onPressed: () async {
                                          SharedPreferences prefs = await SharedPreferences.getInstance();
                                          final int? uId = prefs.getInt('uId');  // send request
                                          putEmail(uId!, myController3.text);
                                          //dispose();
                                          Navigator.pop(context);
                                        },
                                      ),
                                    ],
                                  );
                                },
                              );
                            },
                          ),
                        ),
                      ],
                    ),
                  ),
                  Visibility(
                    visible: uId == curr_user ? true : false,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      children: [
                        Padding(
                          padding: EdgeInsets.all(10),
                          child: Divider(),
                        ),
                        Text(
                          'Demographics',
                          textAlign: TextAlign.left,
                          style: TextStyle(
                            fontSize: 24, 
                            fontWeight: FontWeight.bold, 
                          ),
                        ),
                        //Gender
                        Text(
                          'Gender: ${snapshot.data!.gender}',
                          textAlign: TextAlign.left,
                          style: TextStyle(
                            fontSize: 18, 
                          ),
                        ),
                        //Edit Gender button
                        SizedBox(
                          width: 115,
                          height: 30, 
                          child: ElevatedButton(
                            child: Text(
                              "Edit Gender",
                              style: TextStyle(
                                fontSize: 12
                              ),
                            ),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    title: const Text('Update Gender'),
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
                                        controller: myController4,
                                        decoration: const InputDecoration(
                                          border: OutlineInputBorder(),
                                          hintText: 'Gender',
                                        ),
                                      ),
                                      IconButton(
                                        icon: const Text("Edit"),
                                        tooltip: 'plus',
                                        onPressed: () async {
                                          SharedPreferences prefs = await SharedPreferences.getInstance();
                                          final int? uId = prefs.getInt('uId');  // send request
                                          putGender(uId!, myController4.text);
                                          //dispose();
                                          Navigator.pop(context);
                                        },
                                      ),
                                    ],
                                  );
                                },
                              );
                            },
                          ),
                        ),
                        //Sexual Orientation
                        Text(
                          'Sexual Orientation: ${snapshot.data!.sexualOrientation}',
                          textAlign: TextAlign.left,
                          style: TextStyle(
                            fontSize: 18,
                          ),
                        ),
                        //Edit sexual orientation button
                        SizedBox(
                          width: 180,
                          height: 30, 
                          child: ElevatedButton(
                            child: Text(
                              "Edit Sexual Orientation",
                              style: TextStyle(
                                fontSize: 12
                              ),
                            ),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    title: const Text('Update Sexual Orientation'),
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
                                        controller: myController5,
                                        decoration: const InputDecoration(
                                          border: OutlineInputBorder(),
                                          hintText: 'Sexual orientation',
                                        ),
                                      ),
                                      IconButton(
                                        icon: const Text("Edit"),
                                        tooltip: 'plus',
                                        onPressed: () async {
                                          SharedPreferences prefs = await SharedPreferences.getInstance();
                                          final int? uId = prefs.getInt('uId');  // send request
                                          putSO(uId!, myController5.text);
                                          //dispose();
                                          Navigator.pop(context);
                                        },
                                      ),
                                    ],
                                  );
                                },
                              );
                            },
                          ),
                        ),
                      ]
                    )
                  ),
                  Padding(
                    padding: EdgeInsets.all(10),
                    child: Divider(),
                  ),
                  Text(
                    'Bio',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 24, 
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  // Note
                  Text(
                    '${snapshot.data!.note}',
                    textAlign: TextAlign.left,
                    style: TextStyle(
                      fontSize: 18,
                    ),
                  ),
                  Visibility(
                    visible: uId == curr_user ? true : false,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      mainAxisSize: MainAxisSize.max,
                      children: [
                        //Edit note button
                        SizedBox(
                          width: 90,
                          height: 30, 
                          child: ElevatedButton(
                            child: Text(
                              "Edit Bio",
                              style: TextStyle(
                                fontSize: 12
                              ),
                            ),
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return AlertDialog(
                                    title: const Text('Update Bio'),
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
                                        controller: myController6,
                                        decoration: const InputDecoration(
                                          border: OutlineInputBorder(),
                                          hintText: 'Bio',
                                        ),
                                      ),
                                      IconButton(
                                        icon: const Text("Edit"),
                                        tooltip: 'plus',
                                        onPressed: () async {
                                          SharedPreferences prefs = await SharedPreferences.getInstance();
                                          final int? uId = prefs.getInt('uId');  // send request
                                          putNote(uId!, myController6.text);
                                          //dispose();
                                          Navigator.pop(context);
                                        },
                                      ),
                                    ],
                                  );
                                },
                              );
                            },
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          );
        } else if (snapshot.hasError) { // newly added
          child = Text('${snapshot.error}');
        } else {
          // awaiting snapshot data, return simple text widget
          // child = Text('Calculating answer...');
          child = const CircularProgressIndicator(); //show a loading spinner.
        }
        return child;
      },
    );

    return fb;
  }
}