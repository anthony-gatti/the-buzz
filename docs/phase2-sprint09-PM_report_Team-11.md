# Phase 2 Sprint 9 - PM Report Template

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

Screenshot of Trello board:
![Alt text](trelloBoard.png)

## General questions [15 points total]

1. Did the PM for this week submit this report (If not, why not?)?
    * Yes, the PM submitted this report

2. Has the team been gathering for a weekly, in-person meeting(s)? If not, why not?
    * Yes, the team has been gathering for weekly in-person meetings (Tuesday, Wednesday). We did not meet in person Thursday, as everyone felt the time was productive seperately and instead kept each other updated on progress.

3. Summarize how well the team met the requirements of this sprint.
    * I think that the team did a good job at meeting this weeks requirements, even though we didnt fully complete it. The mobile front ends worked on creating the logic for the deliverables, although they were not able to be tested right away, depending on where the backend was. Backend had a lot of routes to implement and they completed most of them and were available to debug anything relatively quick. Admin created the statements that were needed for development by others.

4. Report on each member's progress (sprint and phase activity completion) – "what is the status?"
    * If incomplete, what challenges are being overcome, how are they being overcome, and by when will the team member be able to finish?
    * If complete, how do you know everyone completed the work, and at a satisfactory level?

5. Summary of "code review" during which each team member discussed and showed their progress – "how did you confirm the status?"
    Code reviews were completed in person. A lot of things were done on the last day, so I was able to be present during the completion.The reviews could have been more in depth, as I looked through the code and documentation looking for any clear issues.

6. What did you do to encourage the team to be working on phase activities "sooner rather than later"?
    * The last sprint was completed early, to give more time within this sprint for implementation. I also encouraged members in the slack to start early and keep working.

7. What did you do to encourage the team to help one another?
     * We decided to meet in person as a group so that there was immediate support and people to bounce ideas off of.

8. How well is the team communicating?
    * Communication within the team is still good. If problems arise, or members are unavailable they bring the concern to light. People are responsive in the slack when needed, although we could work on giving progress updates more frequently, without being asked. Our conversations are mainly centered around the project, but we sometimes do have casual conversations when there is time.

9. Discuss expectations the team has set for one another, if any. Please highlight any changes from last week.
    * The team expectations have not changed, as there hasn't been anything new to address. The current expectations for our team is that we use the class time provided to us to go over any issues people are having, check in with each other and communicate about what still needs to be done. We also aim to check/respond in Slack more frequently, select the next PM on the last in-class meeting of the last PM, have a smooth transfer of PM by the previous PM providing a previous PM report to reference/support and  provide the Trello board and bitbucket information of all the team members, and have every team member aim to start and finish the sprint work as soon as possible.

10. If anything was especially challenging or unclear, please make sure this is [1] itemized, [2] briefly described, [3] its status reported (resolved or unresolved), and [4] includes critical steps taken to find resolution.
    * No challenges really arose, there was immediate debugging so there was no dragging issue
    * Challenge: Creating POST route
        * Status: Not resolved
        * Description: Our backend has not created a post route for users yet
        * Critical steps taken to find resolution: They are tackling that within the prime week (required Oauth) as they wanted the other routes to be done well first

11. What might you suggest the team or the next PM "start", "stop", or "continue" doing in the next sprint?
    * We should continue to get our tasks done on time/ as soon as possible and continue to communicate with each other about challenges, or any concerns to improve our team dynamic
    * I suggest we are more transparent about our progress and what we have worked on already, so that we are not scrambling near the end to tie together any loose ends


## Role reporting [75 points total, 15 points each (teams of 4 get 15 free points)]
Report-out on each role, from the PM perspective.
You may seek input where appropriate, but this is primarily a PM related activity.

### Back-end

1. Overall evaluation of back-end development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * Backend developement was at a good pace and went well. Mostly everything was finished and progres was communicated in slack and visually in trello
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
    * The quality of the code is good. Most functionality needed is implemented, although the developer needs to add more documentation/comments, to make it easier to follow along.

4. Describe the code review process you employed for the back-end.
    * The code review was done in person. I reviewed the flow and documentation of the code and the functionality was demonstrated

5. What was the biggest issue that came up in code review of the back-end server?
    * The only issue that arose during code reviews was the need for the emphasization of documentation by adding more comments.

6. Is the back-end code appropriately organized into files / classes / packages?
    * Yes, the code is organized nicely into appropriate folders, similar to the organization in the tutorials

7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
    * Yes, a dependency for google auth was added but not used.

8. Evaluate the quality of the unit tests for the back-end
    *No unit tests, but the tests were thorough. It showed functionality/correctness of the code using thunder client

9. Describe any technical debt you see in the back-end
    * The route for creating users need to be implemented, as it works cohesively with Oauth. Oauth has not been done.

### Admin

1. Overall evaluation of admin app development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * The admin stuggled a little bit with completion of their tasks. Despite the head start to this sprint, the tables were created later than expected, which prohibited some progress within the backend and frontends. Additionally, not all prepared statements are done for the tables other than messages. Once the admin started their responsibilities there was a steady pace in completion and they reflected so in the trello as well.

2. Describe the tables created by the admin app.
    * There is a table for messages, comments, users, upvotes and downvotes. Messages contain the attributes: subject, message, likes, and id (messages, users). Comments contain the attributes: content, and id (comment, user, message). Users contain the attributes: name, email, gender, username, sexual orientation, note, and id. Upvote contains the attributes: id (message, upvote, user) Lastly, downvote contains the attributes: id (message, user, downvote).

3. Assess the quality of the admin code
    * The quality of the code is good. Most functionality needed by others is implemented. Although, the developer needs to implement prepared statements for other tables and add more documentation/comments, to make it easier to follow along.

4. Describe the code review process you employed for the admin app
     * The code review was done in person. I reviewed the flow and documentation of the code and the functionality was demonstrated

5. What was the biggest issue that came up in code review of the admin app?
    * The only issue that arose during code reviews was the need for the emphasization of documentation by adding more comments.

6. Is the admin app code appropriately organized into files / classes / packages?
    * Yes, the code is organized nicely into appropriate folders, similar to the organization in the tutorials

7. Are the dependencies in the `pom.xml` file appropriate? Were there any unexpected dependencies added to the program?
    * No dependencies were added

8. Evaluate the quality of the unit tests for the admin app
    * No additional unit tests were created for this sprint

9. Describe any technical debt you see in the admin app
    * Functionality for delete, select one, select all, update one for other tables than messages

### Web

1. Overall evaluation of Web development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * Web developement went well and was effecient. Mostly everything was finished and progress was communicated in slack and visually in trello

2. Describe the different models and other templates used to provide the web front-end's user interface.
    * The web front-ends UI, was based directly off the developers mock up.

3. Assess the quality of the Web front-end code
    * The quality of the code is good. Most functionality needed is implemented, although the developer needs to add more documentation/comments, to make it easier to follow along.

4. Describe the code review process you employed for the Web front-end
     * The code review was done in person. I reviewed the flow and documentation of the code and the functionality was demonstrated

5. What was the biggest issue that came up in code review of the Web front-end?
    * The only issue that arose during code reviews was the need for the emphasization of documentation by adding more comments.

6. Is the Web front-end code appropriately organized into files / classes / packages?
    * Yes, the code is organized nicely into appropriate folders

7. Are the dependencies in the `package.json` file appropriate? Were there any unexpected dependencies added to the program?
    * Yes, dependencies for testing were added for using jest and react testing libraries, also dependencies for google authentication.

8. Evaluate the quality of the unit tests for the Web front-end.
    * The unit tests were done using jest/react testing libraries and it was pretty thorough by automating the functionality of the code.

9. Describe any technical debt you see in the Web front-end
    * Unable to actually create a session with the user (Oauth not fully configured)

### Mobile

1. Overall evaluation of Mobile development (how was the process? was Trello used appropriately? how were tasks created? how was completion of tasks verified?)
    * Mobile developement was at a good pace and went well. Mostly everything was finished and progres was communicated in slack and visually in trello

2. Describe the activities that comprise the Mobile app.
    * The mobile app uses an android emulator to display the contents of the app. You can view posts on the home page, upvote and downvote posts, click a post to see comments, create/edit comments, view profiles from a post or comment, and edit your user profile page

3. Assess the quality of the Mobile code.
    * The quality of the code is good. Most functionality needed is implemented and there is documentation that explains what is going on within the code.

4. Describe the code review process you employed for the Mobile front-end
    * The code review was done in person. I reviewed the flow and documentation of the code and the functionality was demonstrated

5. What was the biggest issue that came up in code review of the Mobile front-end?
    *No issues arose, possibly more documentation.

6. Is the Mobile front-end code appropriately organized into files / classes / packages?
    * Yes, the code is organized nicely into appropriate folders, similar to the organization in the tutorials

7. Are the dependencies in the `pubspec.yaml` (or build.gradle) file appropriate? Were there any unexpected dependencies added to the program?
    *Yes, a dependency for google authorization was added

8. Evaluate the quality of the unit tests for the Mobile front-end here
    * No unit tests were created for this sprint

9. Describe any technical debt you see in the Mobile front-end here
    * Unable to actually create a session with the user (Oauth not fully configured)

### Project Management
Self-evaluation of PM performance

1. When did your team meet with your mentor, and for how long?
    * We met with out mentor at 7:00 in CITL for around 60 minutes

2. Describe your use of Trello.  Did you have too much detail?  Too little?  Just enough? Did you implement policies around its use (if so, what were they?)?
    * The requirements that added that each member had to complete was still present, which was detailed (checklists). Members also added more details to their requirements for their own benefits. As they progressed, checklists were marked and tasks were moved to completed. 

3. How did you conduct team meetings?  How did your team interact outside of these meetings?
    * On Tuesday, we met in person as a group, and worked independently with possible support from others if needed. On Wednesday, we went met with our mentor and discussed any questions or concerns any one was having, as well as updated each other on everyone's progress. On Thursday we worked remotely and independently, keeping each other updates on Slack. Lastly, this Tuesday, we met as a group to finish any loose ends and record the video.

4. What techniques (daily check-ins/scrums, team programming, timelines, Trello use, group design exercises) did you use to mitigate risk? Highlight any changes from last week.
    * We had one team meeting through the week to work together ( without mentor ) and complete the sprint deliverables. We set deadline as to when we wanted to complete the sprint and checked in on Slack. Any questions were expressed and answered in Slack when needed. 

5. Describe any difficulties you faced in managing the interactions among your teammates. Were there any team issues that arose? If not, what do you believe is keeping things so constructive?
    * No, there were no issues within managing interactions. I am really happy with our current team dynamic as people keep conversations respectful and no major problems (only expressions of frustration) have arose within members commitment or character.

6. Describe the most significant obstacle or difficulty your team faced.
    * The main obstacle faced was whether or not all the deliverables of the sprint could be met by the deadline. There was a lot of functionality to implement, and the front ends were depending on backend and backend was dependent on admin, which made the process difficult.

7. What is your biggest concern as you think ahead to the next phase of the project? To the next sprint?
    * I do not have any large concerns, the only debt from this sprint is the Google authentication, which I believe can be completed by the end of next sprint.

8. How well did you estimate time during the early part of the phase?  How did your time estimates change as the phase progressed?
    * The time was estimated well during the early part of this phase. We finished the last phase early to account for the amount of time we may need for this current one. Although as time progressed, the time spent needed to be more, which varied on the roles.

9. What aspects of the project would cause concern for your customer right now, if any?
    * The only concern for the customer would be that Oauth is not working correctly, since it is not implemented in the backend yet.