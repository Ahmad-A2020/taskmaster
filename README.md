# taskmaster
### Lab: 26:
- in this lab we created a three pages:
      - homepage: which include the profile photo, and two buttons connected to on click listener that redirect the user to the other pages.
      - Add Task page: two text editor (input label); one for insert title and the other one for insert the body conetnts. at this stage the submit button will only show label once press, and in the next labs other functionality will add.
      - All Tasks page: in this page nothing added till now.

  - ![HomePage Screen Shot](homePage.PNG)


# Lab: 27:
- in this lab we created new two pages
      - task details:  include a title, and body with Lorem Ipsum content
      - setting page: that accept the user name and render it to the homepage.

- ![HomePage Screen Shot](homePage2.PNG)

 ## Lab: 28 - RecyclerView
in this lab we created a recycle view for diplay a list of data at the all list page.
 - ![All Tasks](tasksList.PNG)

## Lab: 29 - Room
 in this lab we user the room, which is an ORM (object relational mapper) for SQLite database in Android. to save data (task) at the database, and then invoke all the stored data (tasks) at the all tasks layout.

## Lab: 31 - Espresso and Polish
in this lab, we create a test function for the the user interface using of Espresso framework.

## Lab: 32 - Amplify and DynamoDB:
 in this lab, we created cloud storage, on which each time the data saved it, hit the aws server , and store at the database.Also, the at the all list activity will fetch all the data (tasks) form the server and list them using recycle view.

## Lab: 33 - Related Data:
in this lab, we created a new model at the graphql to assign the task to the team. At the biggining we hit the server (database) and save three teams for one time (teamA,B,C). After that we added a spinner at the add task activity to assign the task at one of the three teams.

## Lab: 34 - Publishing to the Play Store:
in this lab we build the APK, which is the Android application package file format used by the Android operating system. this steps is neccessary to upload the application to the google play. As uploading the application to google play needs to have a developer account that  a one-time fee of $25, I have not published it on google play, but in the future I will create account and lunch this project.

## Lab: 36 - Cognito:
in this lab we added an authentication system to our android application using of amplify auth. the user can create a user name and verify it using email. For this purpose signUp, Login, verification activities were created. Also, a new features added on the application such as showing the user name.

## Lab: 37 - S3
In this lab we used another service offer by akmazon which S3 storage so that we can upload files and used it later (download )