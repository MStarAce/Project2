# Project2
neighborhood app

- Study Buddy

-This app searches and database that is hardcoded to be created upon installation. The database is only created once as shared preferences are used to not contiue to add to the database when "SearchActivity" is destroyed and creataed.
-There are three search types that can be carried out. A traditional criteria search that captures user input to serach and return results orderd by distance from the user. There is also a buddy search. With one button a user can pull up a result list of all the buddies they have saved. The final serach type is through the search option on the menu. This search is for finding specific users.
-The logged in user also has the ability to add and remove their saved interest. There is an edit button on the menu bar that brings the user to their interests detail activity. From there the user can delete with a long click or add with the FAB button.

- Known Bugs

-this app doens't have a login screen. One user is always logged in.
-the user would need the ability to suggest to the developer more Subjects, and levels because they were hard coded to make searching easier and more efficient.


- Quality Testing

1) user can not crash the app with inputs made on the search page
2) user returns nothing when search is should return no results ie "Art History" is a subject that has no users associated with it.
3) buddy search returns the correct users
4) detail view allows user to add or remove friends and the database is updated(after returning to the result activity) buddy search is updated and user no longer appears or in the case of a criteria and userName search the result is updated with appropriate image
5) The edit button on the options menu correctly displays the users interests
6) The long click feature of the edit activity deletes only pressed item and updates activity to remove it from list view
7) The add feature handles user input and doens't crash and new interest is added to the database
8) The changes made by, the friends and unfrined feature, and the interest edit feature, persist properly with shared preferences


