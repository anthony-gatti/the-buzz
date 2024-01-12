# The Buzz Social Media App

## Details
* Last Updated: Fall 2023
# Student names: 
* Bukky Omole: Mobile Front-end
* Anthony Gatti: Web Front-end
* Robert Magnus: Admin-cli
* Matthew Bergin: Back-end
* Madix Marlatt: PM

# Design Artifacts: 
    * <https://drive.google.com/drive/folders/1ndRNPwlY8R8ooJwsJn0jKbak7ogIL6aZ>
    
# Database:
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

## Sprint 12 Functionality

* Current functionality:
        *Mobile: can access profile from posts and comments, shows a list of all users, can display posts, can create posts, create/edit/view comments, can upvote or downvote a post, can create a user/profile 
        *Web: can display posts, can create posts, create/edit/view comments, can upvote or downvote a post, can access user profile from navbar/comments/posts, can edit profile, can create a user/user profile
        *Backend: Everything listed on the rubric is operational, can upload and retrieve files from google drive service account, files and users are stored in memcachier, can also upload links, links and files can be edited on comments only. Optional feature of using http response headers to aid clients in using standard web app caching is not implemented currently
        *Admin: all tables created and prepared statements done, all basic Admin functionality is present such as GET, PUT etc for each table, and added validation and invalidation of messages, comments, and users .OAuth not implemented yet

## Documentation

* Admin
        * [App.java Docs](../admin-cli/javadocArtifacts/edu/lehigh/cse216/rdm325/admin/App.html)
        * [Database.java Docs](../admin-cli/javadocArtifacts/edu/lehigh/cse216/rdm325/admin/Database.html)
* Backend
        * [Main directory](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/package-tree.html)
        * [App.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/App.html)
        * [Database.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/Database.html)
        * [DataRowComment.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/DataRowComment.html)
        * [DataRowMessage.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/DataRowMessage.html)
        * [DataRowUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/DataRowUser.html)
        * [SimpleRequestMessage.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/SimpleRequestMessage.html)
        * [SimpleRequestComment.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/SimpleRequestComment.html)
        * [SimpleRequestUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/SimpleRequestUser.html)
        * [CreateAuth.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/CreateAuth.html)
        * [StructuredResponse.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/StructuredResponse.html)
        * [StructuredResponseUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/StructuredResponseUser.html)
        * [StructuredResponseUser.java](target/site/apidocs/edu/lehigh/cse216/rdm325/backend/StructuredResponseUser.html)
* Web
        * [Frontend](/web/out/App.jsx.html)
        * [Bio Docs](web\out\components_Bio.jsx.html)
        * [Comment Docs](web\out\components_Comment.jsx.html)
        * [DeletePost Docs](web\out\components_DeletePost.jsx.html)
        * [Demographics Docs](web\out\components_Demographics.jsx.html)
        * [DisplayMessage Docs](web\out\components_DisplayMessage.jsx.html)
        * [DisplayPost Docs](web\out\components_DisplayPost.jsx.html)
        * [Header Docs](web\out\components_Header.jsx.html)
        * [LikePost Docs](web\out\components_LikePost.jsx.html)
        * [NewComment Docs](web\out\components_NewComment.jsx.html)
        * [PersonalInfo Docs](web\out\components_PersonalInfo.jsx.html)

* Mobile
        * [LoginAPi.dart Docs](mobile\flutter\my_tutorial_app\doc\api\login_api\LoginAPi\LoginAPi.html)
        * [GoogleSignInApi.html Docs](mobile\flutter\my_tutorial_app\doc\api\google_signin_api\GoogleSignInApi\GoogleSignInApi.html)
        * [getMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_getMessage/getMessage.html)
        * [getMessages.dart Docs](flutter/my_tutorial_app/doc/api/net_getMessages/getMessages.html)
        * [postMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_postMessage/postMessage.html)
        * [putLikeMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_putLikeMessages/getMessages.html)
        * [putUnlikeMessage.dart Docs](flutter/my_tutorial_app/doc/api/net_putUnlikeMessages/getMessages.html)
        * [Message.dart](mobile/flutter/my_tutorial_app/doc/api/models_Message/models_Message-library.html)
        * [main.dart](mobile/flutter/my_tutorial_app/doc/api/main/main-library.html)
        * [Comment.dart Docs](mobile\flutter\my_tutorial_app\doc\api\models_Comment\models_Comment-library.html)
        * [Downvote Docs](mobile\flutter\my_tutorial_app\doc\api\models_Downvote\models_Downvote-library.html)
        * [NumberWordPair.dart Docs](mobile\flutter\my_tutorial_app\doc\api\models_NumberWordPair\NumberWordPair\NumberWordPair.html)
        * [Upvote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\models_Upvote\models_Upvote-library.html)
        * [User.dart Docs](mobile\flutter\my_tutorial_app\doc\api\models_User\models_User-library.html)
        * [deleteDownvote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_deleteDownvote\deleteDownvote.html)
        * [deleteUpvote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_deleteUpvote\deleteUpvote.html)
        * [getComments.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_getComments\getComments.html)
        * [getDownvote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_getDownvote\getDownvote.html)
        * [getUpvote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_getUpvote\getUpvote.html)
        * [getUser.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_getUser\getUser.html)
        * [postComment.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_postComment\postComment.html)
        * [postDownvote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_postDownvote\postDownvote.html)
        * [postUpvote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_postUpvote\postUpvote.html)
        * [putComment.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putComment\putComment.html)
        * [putEmail.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putEmail\putEmail.html)
        * [putGender.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putGender\putGender.html)
        * [putLikeMessage.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putLikeMessage\putLikeMessage.html)
        * [putName.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putName\putName.html)
        * [putNote.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putNote\putNote.html)
        * [putSO.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putSO\putSO.html)
        * [putUsername.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_putUsername\putUsername.html)
        * [webRequests.dart Docs](mobile\flutter\my_tutorial_app\doc\api\net_webRequests\getWebData.html)