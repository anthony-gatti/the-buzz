# CSE 216 - Software Engineering Tutorials

## Team Information

​
Team Information:

* Number: 11
* Name: 7-11
* Mentor: Sunny You, <suy224@lehigh.edu>
* Weekly live & synchronous meeting:
    *without mentor: Thursday and Tuesday during time given in class
    *with mentor: Wednesday at 7PM in CITL
​
Team Members, Roles, and links to trello & repos:
​
* Robert Magnus, <rdm325@lehigh.edu> Backend
* Matthew Bergin, <mdb326@lehigh.edu> Web Frontend
* Anthony Gatti, <amg625@lehigh.edu> Mobile
* Bukky Omole, <ooo225@lehigh.edu> PM
* Madix Marlatt, <mtm225@lehigh.edu> Admin

Trello Board:

* <https://trello.com/b/KuM41xIw/7-11-board>

Bitbucket:

* <https://bitbucket.org/rdm325/cse216-2023fa-team-11/src/master/>

Database:

* peanut.db.elephantsql.com

## Launching the moblie App environment

* In VSCode, click on the devices tab on the bottom bar.
* click on "Start Flutter Emulator", or Create Android Emulator if the former option isn't available.
* open `main.dart` in VSCode and click on the start debugging button.
* enjoy the mobile frontend.

## Run on Dokku AND Run Locally

* Assuming you can ssh into Dokku on this device (from the Lehigh wifi/vpn), ren the following in a terminal:
        * `ssh -i ~/.ssh/id_ed25519 -t dokku@dokku.cse.lehigh.edu 'ps:start 2023fa-tutorial-team-7-11'`
        * run: `ssh -i ~/.ssh/id_ed25519 -t dokku@dokku.cse.lehigh.edu 'config:set 2023fa-tutorial-team-7-11 CORS_ENABLED=true'` if accessing/testing from the same device you are launching the app from.
* When done accessing the site on Dokku
        * run `ssh -i ~/.ssh/id_ed25519 -t dokku@dokku.cse.lehigh.edu 'config:set 2023fa-tutorial-team-7-11 CORS_ENABLED=false'` if you turned on Cors earlier
        * run `ssh -i ~/.ssh/id_ed25519 -t dokku@dokku.cse.lehigh.edu 'ps:stop 2023fa-tutorial-team-7-11'` to stop Dokku from running the site
* to use the app while it's running:
        *go to [2023fa-tutorial-team-7-11.dokku.cse.lehigh.edu/messages](2023fa-tutorial-team-7-11.dokku.cse.lehigh.edu/messages)
        * go to localhost:8080 after launching running `sh local-deploy.sh` in the web directory.

## Running the Web App Locally

* Ensure connection to Lehigh wifi and navigate to the web folder
        *install the node modules with the command `npm install`
        *launch the server with the command `npm run dev`

## Sprint 8 Deliverables

* Listing of user stories for admin and "anonymous user" personas are listed on the Trello board (link above)
* System drawing: ./SystemDiagram2.pdf
* Mock web interface: ./webUI (.png)
* Mock mobile interface: ./mobileUI (.pdf)
* User FSM Diagram: ./UserFSM_Diagram (10-26).pdf
* Idea FSM Diagram: ./IdeaFSM_Diagram.pdf
* Entity relationship diagram: ./Phase2_ERD.pdf
* Routes: ./TheBuzzAPI_list.pdf
* User stories tests, and unit test descriptions are all listed in the Trello board
* Backlog: web and mobile app styling

## Sprint 10 Functionality

* Current functionality:
        *Mobile: can access profile from posts and comments, shows a list of all users, can display posts, can create posts, create/edit/view comments, can upvote or downvote a post, can create a user/profile 
        *Web: can display posts, can create posts, create/edit/view comments, can upvote or downvote a post, can access user profile from navbar/comments/posts, can edit profile, can create a user/user profile
        *Backend: all specified routes implemeneted
        *Admin: all tables created and prepared statements done

## Documentation

* Admin
        *[App.java Docs](../admin-cli/javadocArtifacts/edu/lehigh/cse216/rdm325/admin/App.html)
        *[Database.java Docs](../admin-cli/javadocArtifacts/edu/lehigh/cse216/rdm325/admin/Database.html)
* Backend
        *[Main directory](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/package-tree.html)
        * [App.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/App.html)
        *[Database.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/Database.html)
        * [DataRowComment.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/DataRowComment.html)
        *[DataRowMessage.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/DataRowMessage.html)
        * [DataRowUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/DataRowUser.html)
        *[SimpleRequestMessage.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/SimpleRequestMessage.html)
        * [SimpleRequestComment.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/SimpleRequestComment.html)
        *[SimpleRequestUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/SimpleRequestUser.html)
        * [CreateAuth.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/CreateAuth.html)
        *[StructuredResponse.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/StructuredResponse.html)
        * [StructuredResponseUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/StructuredResponseUser.html)
        * [StructuredResponseUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/StructuredResponseUser.html)
* Web
        *[Frontend](../web/out/App.jsx.html)
* Mobile
        * [getMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_getMessage/getMessage.html)
        * [getMessages.dart Docs](flutter/my_tutorial_app/doc/api/net_getMessages/getMessages.html)
        * [postMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_postMessage/postMessage.html)
        * [putLikeMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_putLikeMessages/getMessages.html)
        * [putUnlikeMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_putUnlikeMessages/getMessages.html)
        * [Message.dart](mobile/flutter/my_tutorial_app/doc/api/models_Message/models_Message-library.html)
        * [main.dart](mobile/flutter/my_tutorial_app/doc/api/main/main-library.html)
