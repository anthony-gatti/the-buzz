# Phase 2' Sprint 10 - PM Report 
## Team Information [10 points total]

### Team Information:

* Number: 11
* Name: Bukky Omole
* Mentor: <Sunny You, suy224@lehigh.edu>
* Weekly live & synchronous meeting:
        *without mentor: Tuesday and Thursday during allotted class time
        *with mentor: Wednesdays, 7 pm at CITL

### Team Roles:

* Project Manger: <Bukky Omole, ooo225@lehigh.edu>
  * Has this changed from last week (if so, why)? No
* Backend developer: <Robert Magnus, rdm325@lehigh.edu>
* Admin developer: <Madix  Marlatt, mtm225@lehigh.edu>
* Web developer: <Matthew Bergin, mdb326@lehigh.edu>
* Mobile developer: <Anthony Gatti, amg625@lehigh.edu>

### Essential links for this project:

* Team's Dokku URL(s)
    *<https://team-7-11.dokku.cse.lehigh.edu>
* Team's software repo (bitbucket)
    *<https://bitbucket.org/rdm325/cse216-2023fa-team-11/src/master/>
* Team's Trello board
    *<https://trello.com/b/KuM41xIw/7-11-board>
* Database:
    *<https://peanut.db.elephantsql.com>


## Beginning of Phase 2' [20 points total]
Report out on the Phase 2 backlog and any technical debt accrued during Phase 2.

1. What required Phase 2 functionality was not implemented and why? 
        * Admin-cli: the prepared statements for the tables other the "messages" table needed to be implemented
        * Backend: POST users route needed to be implemeted (OAUTH sessions)
        * Mobile FE: Oauth not fully working (no users)
        * Web FE: Oauth not fully working (no users)

    The functionaliy was not implemented from lack of time

2. What technical debt did the team accrue during Phase 2?
        * Admin-cli:  None
        * Backend: None
        * Mobile FE:  None
        * Web FE: running locally


## End of Phase 2' [20 points total]
Report out on the Phase 2' backlog as it stands at the conclusion of Phase 2'.

1. What required Phase 2 functionality still has not been implemented or is not operating properly and why?
    * All components are working as they should.

2. What technical debt remains?
    * The web front end is still running locally instead of on Dokku

3. If there was any remaining Phase 2 functionality that needed to be implemented in Phase 2', what did the PM do to assist in the effort of getting this functionality implemented and operating properly?
    * The PM was up to date on what functionality was missing and looked for progress updates from members to keep them on track

4. Describe how the team worked together in Phase 2'. Were all members engaged? Was the work started early in the week or was there significant procrastination?
    * The team worked well together during the prime week. Everyone was aware of what they needed to implement and worked on it day by day. There was no significant procrastination in responsibilities.

5. What might you suggest the team or the next PM "start", "stop", or "continue" doing in the next Phase?
    * We should continue to get our tasks done on time/ as soon as possible and continue to communicate with each other about challenges, or any concerns to improve our team dynamic
    * I suggest we are more transparent about our progress and what we have worked on already, so that we are not scrambling near the end to tie together any loose ends

## Role reporting [50 points total]

### Back-end
What did the back-end developer do during Phase 2'?
1. Overall evaluation of back-end development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * The development of the backend was very steady. They were very responsive to issues and active with debugging. They were finished with the code before we met to record
2. List your back-end's REST API endpoints
    The Buzz API List: (backend_url/api/v2.0)
        ● Messages:
            ○ GET /messages
            ○ GET /messages/:id
            ○ POST /messages
            ○ POST /messages/upvote/:mId
            ○ POST /messages/downvote/:mId
            ○ DEL. /messages/upvote/:mId
            ○ DEL. /messages/downvote/:mId
        ● Comments:
            ○ GET /comments/:cId
            ○ GET /messages/comments/:mId
            ○ POST /comments/:mId
            ○ PUT /comments/:cId
        ● Users:
            ○ GET /users/:id
            ○ POST /users
            ○ PUT /users/name/:id
            ○ PUT /users/email/:id
            ○ PUT /users/gender/:id
            ○ PUT /users/so/:id
            ○ PUT /users/username/:id
            ○ PUT /users/note/:id
3. Assess the quality of the back-end code
    * The quality of the backend code is good. The developer added comments and had good documentation for their files to make it easier to follow along and understand
4. Describe the code review process you employed for the back-end
    * For the code review process, I sat with the individual in person and went through their pull request. I looked for good documentation, organization and flow through the files and asked them about the functionality.
5. What was the biggest issue that came up in code review of the back-end server?
    * No issues, last week was the documentation, but that was remedied during the prime week
6. Is the back-end code appropriately organized into files / classes / packages?
    * Yes the structure/organization is similar to the tutorials
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
    * Yes, the dependencies are appropriate and the google authentication dependency was added during the last sprint but finally used during this sprint.
8. Evaluate the quality of the unit tests for the back-end
    * The unit tests for the backend thoroughly test the functionality and additonally the code is tested by how the frontend reacts. Additionally to unit tests, the developer used thunder client to test.
9. Describe any technical debt you see in the back-end
    *None, everything is working as it should

### Admin
What did the admin front-end developer do during Phase 2'?
1. Overall evaluation of admin app development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * The development of the admin was good. They implemented what was important for others first and did the other things after. They had some last minute things to add when we met, but it did not take a very long time, so it was okay. 
2. Describe the tables created by the admin app
    * There is a table for messages, comments, users, upvotes and downvotes. Messages contain the attributes: subject, message, likes, and id (messages, users). Comments contain the attributes: content, and id (comment, user, message). Users contain the attributes: name, email, gender, username, sexual orientation, note, and id. Upvote contains the attributes: id (message, upvote, user) Lastly, downvote contains the attributes: id (message, user, downvote).
3. Assess the quality of the admin code
    * The quality of the admin code is good. The developer added comments and had good documentation for their files to make it easier to follow along and understand
4. Describe the code review process you employed for the admin app
    * For the code review process, I sat with the individual in person and went through their pull request. I looked for good documentation, organization and flow through the files and asked them about the functionality.
5. What was the biggest issue that came up in code review of the admin app?
    * No issues, last week was the documentation, but that was remedied during the prime week
6. Is the admin app code appropriately organized into files / classes / packages?
    * Yes the structure/organization is similar to the tutorials
7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
    * Yes, the dependencies are appropriate and none were added
8. Evaluate the quality of the unit tests for the admin app
    * The tests for admin were good and tested the functionality. The admin was also tested in relationship with the other roles and if it worked.
9. Describe any technical debt you see in the admin app
    *None, everything is working as it should

### Web
What did the web front-end developer do during Phase 2'?
1. Overall evaluation of Web development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * The development of the web was great. They did a good job at working around what was not finished and created the overall logic, which they tested later. They finished their implementation before we met and everything looked good.
2. Describe the different models and other templates used to provide the web front-end's user interface
    * The web developer used an online editor to create the web UI model and decided to keep the colors of the interface dark and practical
3. Assess the quality of the Web front-end code
    * The quality of the web code is good. The developer added comments and had good documentation for their files to make it easier to follow along and understand
4. Describe the code review process you employed for the Web front-end
    * For the code review process, I sat with the individual in person and went through their pull request. I looked for good documentation, organization and flow through the files and asked them about the functionality.
5. What was the biggest issue that came up in code review of the Web front-end?
    * No issues, last week was the documentation, but that was remedied during the prime week
6. Is the Web front-end code appropriately organized into files / classes / packages?
    * Yes the structure/organization is good, it seperates the components and tests etc into appropriate folders
7. Are the dependencies in the `package.json` file appropriate? Were there any unexpected dependencies added to the program?
    * Yes, the dependencies are appropriate and the google authentication dependency was added last sprint
8. Evaluate the quality of the unit tests for the Web front-end 
    * The quality of the tests were good for the web and tested the functionality well (automated). Additionally, the app was manually tested.
9. Describe any technical debt you see in the Web front-end
    *Everything is working as it should, although it is still running locally

### Mobile
What did the mobile front-end developer do during Phase 2'?
1. Overall evaluation of Mobile development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * The development of mobile was good. They kept up with their responsibilities and were very active with implementing what was needed. They had one issue they struggled to figure out with the upvote/downvote animation, but it was delt with by the time we met.
2. Describe the activities that comprise the Mobile app
    * The mobile app uses an android emulator to display the contents of the app. You can view posts on the home page, upvote and downvote posts, click a post to see comments, create/edit comments, view profiles from a post or comment, and edit your user profile page/ create a user
3. Assess the quality of the Mobile code
    * The quality of the mobile code is good. The developer added comments and had good documentation for their files to make it easier to follow along and understand
4. Describe the code review process you employed for the Mobile front-end
    * For the code review process, I sat with the individual in person and went through their pull request. I looked for good documentation, organization and flow through the files and asked them about the functionality.
5. What was the biggest issue that came up in code review of the Mobile front-end?
    * No issues, last week was the documentation, but that was remedied during the prime week
6. Is the Mobile front-end code appropriately organized into files / classes / packages?
    * Yes the structure/organization is similar to the tutorials
7. Are the dependencies in the `pubspec.yaml` (or build.gradle) file appropriate? Were there any unexpected dependencies added to the program?
    * Yes, the dependencies are appropriate and the google authentication was added during the last sprint but finally used during this sprint.
8. Evaluate the quality of the unit tests for the Mobile front-end here
    * The unit tests for the mobile looked good and tested the functionality for the app. Additionally, the app was manually tested.
9. Describe any technical debt you see in the Mobile front-end here
    *None, everything is working as it should

### Project Management
Self-evaluation of PM performance
1. When did your team meet with your mentor, and for how long?
    * We met with our mentor on Wednesday in CITL at 7pm for about 40 minutes
2. Describe your use of Trello.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
    * The use of trello for this sprint was not really necessary. Each member only had about one item of backlog they needed to complete. Once that was done, the use of trello was unneeded.
3. How did you conduct team meetings?  How did your team interact outside of these meetings?
    * Before team meetings I would ask about the work intensity and how each team member could support each other. Depending on responses, we decided if we should meet in person or keep each other updated on slack. This past week we had team meetings in person and outside of the meetings we asked questions/made comments in slack
4. What techniques (daily check-ins/scrums, team programming, timelines, Trello use, group design exercises) did you use to mitigate risk? Highlight any changes from last week.
    *  We had an in person team meeting on Thursday and went over details with our mentor on Wednesday. We communicated through Slack with any issues while working independently.
5. Describe any difficulties you faced in managing the interactions among your teammates. Were there any team issues that arose? If not, what do you believe is keeping things so constructive? 
    * Ther were not really any difficulties managing team interactions. Everyone is respectful and mostly friendly. There are occasional expressions of frustrations at times but no big issues have arose. Our team just has a mutual understanding to be respectful and helpful to each other.
6. Describe the most significant obstacle or difficulty your team faced.
    * The biggest obstacle our team faced during this phase was google authentication and the session keys. It was not implemented til the end to make sure it was done correctly.
7. What is your biggest concern as you think ahead to the next phase of the project? To the next sprint? What steps can the team take to reduce your concern?
    * The biggest concern for the next sprint would be uploading files onto Buzz. It is the next big task we are challenged with. The team can support each other when help is needed and communicate well about progress
8. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?
    * The time was estimated very well. Every paced themselves very good, on the last day we met to record, there were not many last minute issues. The problems that were apparent were resolved within about half an hour. The time estimates remained the same
9. What aspects of the project would cause concern for your customer right now, if any?
    *Nothing, everything is working as it should
