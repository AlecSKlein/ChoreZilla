>>Documents:

- Added sequence and activity diagrams for functionality implemented (Documentation.docx)

- Class diagram needed to be edited (see Sprint3ClassDiagram.pdf)

- Updated Sprint schedule (SprintSchedule4.pdf)


>> To run Code:
- Open a copy of Eclipse that has ADK installed.
- To Run code, either create emulator or use phone with AT LEAST Android 2.3.3 (API 14), Target is 4.2 (API 18).

- Create new user (fyi - passwords are unimplemented/ignored for now).
- You can now login with that username over and over again.
- The Edit Lists Screen will appear after signing in.
- You can choose which list you'd like to view with the spinner at the top of the screen, or create new list.
- Click "Add Item" to add a new item to the selected list
- Click "Edit List Name" to change the name of the selected list
- Click "Add User" to add a user to the selected list
- Click on an item to edit it (editing dates is unimplemented as of now)
- Click phone's back button to return to previous screen.


>> Changes:
- Changed implementation for Create New User (method in RegisterScreen is changed, and two new classes are added: UserCtrl.java, UserProfile.java), changed how user information is stored in system, minor tweaks...
- Sign In: username and password are both checked and validated from Android File system (will be changed to DB later).
- "Remember Me" check box is functional now.
- Adding lists is now fully functional.
- STUBS added for DAOs
- Fixed minor crashes resulting from null pointer exceptions in special cases.
	

>>Resources:

- Test XML file is in the assets folder
- Text files store user/password combos, remember me
