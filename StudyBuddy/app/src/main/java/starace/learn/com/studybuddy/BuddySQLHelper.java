package starace.learn.com.studybuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * SQLiteOpenHelper Class
 *
 * pre-populates database tables
 * adds and removes from table
 */
public class BuddySQLHelper extends SQLiteOpenHelper {

    private static final String TAG_HELPER = "BuddySQLHelper";

    //<editor-fold desc="Database Variables">
    private static final  int DB__BUDDY_VERSION = 1;
    public static final String DB_BUDDY_NAME = "buddy_db";
    //</editor-fold>

    //<editor-fold desc="Buddy Table Variables">
    public static final String BUDDY_TABLE_NAME = "buddy";
    public static final String BUDDY_COLUMN_ID = "_id";
    public static final String BUDDY_COLUMN_USERNAME = "_user_name";
    public static final String BUDDY_COLUMN_FIRSTNAME = "first_name";
    public static final String BUDDY_COLUMN_LASTNAME = "last_name";
    public static final String BUDDY_COLUMN_CITY = "city";
    public static final String BUDDY_COLUMN_STATE = "state";
    public static final String BUDDY_COLUMN_DISTANCE = "distance";
    public static final String BUDDY_COLUMN_ISBUDDY = "buddy_image";
    public static final String BUDDY_COLUMN_IMAGE = "user_image";
    public static final String[] BUDDY_COLUMN_ALL = {BUDDY_COLUMN_ID ,BUDDY_COLUMN_USERNAME, BUDDY_COLUMN_FIRSTNAME,
            BUDDY_COLUMN_LASTNAME,BUDDY_COLUMN_CITY,BUDDY_COLUMN_STATE,BUDDY_COLUMN_DISTANCE,BUDDY_COLUMN_ISBUDDY
            ,BUDDY_COLUMN_IMAGE};
    public static final String TABLE_BUDDY_CREATE = "CREATE TABLE buddy (" +BUDDY_COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + BUDDY_COLUMN_USERNAME + " TEXT NOT NULL," +
            BUDDY_COLUMN_FIRSTNAME + " TEXT," + BUDDY_COLUMN_LASTNAME + " TEXT," + BUDDY_COLUMN_CITY + " TEXT," +
            BUDDY_COLUMN_STATE + " TEXT," + BUDDY_COLUMN_DISTANCE + " INTEGER," +BUDDY_COLUMN_ISBUDDY+ " INTEGER," +
            BUDDY_COLUMN_IMAGE+ " INTEGER );";

    public static final String TABLE_BUDDY_DROP = "DROP TABLE IF EXISTS buddy;";

    public static final int IMAGE_BUDDY = R.drawable.buddy;
    public static final int IMAGE_NO = R.drawable.no_image;
    public static final int IMAGE_USER1 = R.drawable.rface1;
    public static final int IMAGE_USER2 = R.drawable.babyface;
    public static final int IMAGE_USER3 = R.drawable.ouch;
    public static final int IMAGE_USER4 = R.drawable.prez;
    public static final int IMAGE_USER5 = R.drawable.sun;
    public static final int IMAGE_USER6 = R.drawable.tips;
    public static final int IMAGE_USER8 = R.drawable.bad;
    //</editor-fold>

    //<editor-fold desc="Interest Table Variables">
    public static final String INTEREST_TABLE_NAME = "interest";
    public static final String INTEREST_COLUMN_ID = "_id";
    public static final String INTEREST_COLUMN_USER_NAME = "user_name";
    public static final String INTEREST_COLUMN_SUBJECT = "subject";
    public static final String INTEREST_COLUMN_LEVEL = "level";
    public static final String INTEREST_COLUMN_CLASS = "class";
    public static final String[] INTEREST_COLUMN_ALL = {INTEREST_COLUMN_ID,INTEREST_COLUMN_USER_NAME,INTEREST_COLUMN_SUBJECT,
            INTEREST_COLUMN_LEVEL, INTEREST_COLUMN_CLASS};
    public static final String TABLE_INTEREST_CREATE = "CREATE TABLE interest (" +INTEREST_COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + INTEREST_COLUMN_USER_NAME+ " TEXT NOT NULL, " +
            INTEREST_COLUMN_SUBJECT + " TEXT, " + INTEREST_COLUMN_LEVEL + " TEXT, " + INTEREST_COLUMN_CLASS +
            " TEXT, FOREIGN KEY " + "(user_name) REFERENCES buddy (_user_name));";

    public static final String TABLE_INTEREST_DROP = "DROP TABLE IF EXISTS interest;";
    //</editor-fold>

    //<editor-fold desc="Friend Table Variables">
    public static final String FRIEND_COLUMN_ID = "_id";
    public static final String FRIEND_TABLE_NAME = "friend";
    public static final String FRIEND_COLUMN_USERNAME = "user_name";
    public static final String FRIEND_COLUMN_BUDDY = "buddy";
    public static final String[] FRIEND_COLUMN_ALL = {FRIEND_COLUMN_ID,FRIEND_COLUMN_USERNAME, FRIEND_COLUMN_BUDDY};
    public static final String TABLE_FRIEND_CREATE = "CREATE TABLE friend (" + FRIEND_COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+ FRIEND_COLUMN_USERNAME + " TEXT NOT NULL, " +
            FRIEND_COLUMN_BUDDY + " TEXT NOT NULL);";
    public static final String TABLE_FRIEND_DROP = "DROP TABLE IF EXISTS friend;";
    //</editor-fold>

    public BuddySQLHelper(Context context) {
        super(context, DB_BUDDY_NAME, null, DB__BUDDY_VERSION);
    }

    /**
     * Creates tables in the database buddy_db
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_BUDDY_CREATE);
        db.execSQL(TABLE_INTEREST_CREATE);
        db.execSQL(TABLE_FRIEND_CREATE);

    }

    /**
     * onUpgrade Deletes any existing tables and then calls on create to recreate them
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_BUDDY_DROP);
        db.execSQL(TABLE_INTEREST_DROP);
        db.execSQL(TABLE_FRIEND_DROP);
        this.onCreate(db);

    }

    private static BuddySQLHelper instance;

    /**
     * singleton for the SQLiteOpenHelper
     * @param context
     * @return
     */
    public static BuddySQLHelper getInstance(Context context) {
        if (instance == null){
            instance =  new BuddySQLHelper(context.getApplicationContext());
        }

        return instance;
    }

    //<editor-fold desc="Buddy Table Methods">
    /**
     * Takes a ArrayList of users and updates the database "buddy" table with images
     * @param userName
     */
    public void addBuddyImage(ArrayList<String> userName) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < userName.size(); i++) {
            db.execSQL("UPDATE buddy SET " + BUDDY_COLUMN_ISBUDDY + " = " +
                    IMAGE_BUDDY + " WHERE " + BUDDY_COLUMN_USERNAME + " = '" + userName.get(i) + "';");
        }
    }

    /**
     * Adds an image resource integer to a single user in the "buddy" table
     * @param user
     */
    public void addSingleBuddyImage(String user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE buddy SET " + BUDDY_COLUMN_ISBUDDY + " = " +
                IMAGE_BUDDY + " WHERE " + BUDDY_COLUMN_USERNAME + " = '" + user + "';");
    }

    /**
     * removes an image resource integer from a single user in the "buddy" table
     * @param user
     */
    public void removeSingleBuddyImage(String user){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE buddy SET " + BUDDY_COLUMN_ISBUDDY + " = " + null + " WHERE " + BUDDY_COLUMN_USERNAME + " = '" +
                user + "';");

    }

    /**
     * takes an input of of an ArrayList of users and returns each users data to the cursor
     * @param userName
     * @return
     */
    public Cursor getUserDataFromList(ArrayList<String> userName){
        String[] arguments = new String[userName.size()];
        arguments = userName.toArray(arguments);

        String conditions;
        conditions = BUDDY_COLUMN_USERNAME + " = ? ";

        for (int i = 1; i < userName.size(); i++){
            conditions = conditions + "OR " + BUDDY_COLUMN_USERNAME + " = ? ";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BUDDY_TABLE_NAME, BUDDY_COLUMN_ALL,
                conditions,
                arguments,
                null,
                null,
                BUDDY_COLUMN_DISTANCE);
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * returns a cursor with the search data from a single user
     * @param userName
     * @return
     */
    public Cursor getUserDataFromList(String userName){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BUDDY_TABLE_NAME, BUDDY_COLUMN_ALL,
                BUDDY_COLUMN_USERNAME+ " = ? ",
                new String[]{userName},
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }
    //</editor-fold>

    //<editor-fold desc="Table Fill Methods">
    /**
     * fills the "buddy" table in the database with hardcoded values
     */
    public void fillBuddyTable(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'MrPerfect', 'Billy', 'Bob', 'Piedmont', 'CA', 3,null,"+IMAGE_NO+");");
        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'DrStudy', 'Gloria', 'Sanchez', 'Richmond', 'CA', 6,null," +IMAGE_USER1+");");
        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'BigD', 'Dave', 'Roberts', 'San Leandro', 'CA', 4,null,"+IMAGE_USER2+");");
        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'ZiggyP', 'Zane', 'Proctor', 'SF', 'CA', 2,null,"+IMAGE_USER3+ ");");
        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'Jane88', 'Jane', 'Driver', 'Oakland', 'CA', 0,null,"+IMAGE_USER4+");");
        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'JavaLady', 'Sadie', 'Serotte', 'Mill Valley', 'CA', 12, null,"+IMAGE_USER5+");");
        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'CatzFan', 'Jesus', 'Rodriguez', 'Daily City', 'CA', 8, null, "+IMAGE_USER6+");");
        db.execSQL("INSERT INTO " + BUDDY_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'Michael', 'Starace','Oakland','CA', 0, null," + IMAGE_USER8 + ");");
    }

    /**
     * fills the "interest" table in the database with hardcoded values
     */
    public void fillInterestTable(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MrPerfect','Biology', 'High School', 'biology 101');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MrPerfect','Chemistry', 'High School', 'chemistry 101');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'DrStudy','Computer Science',null,null);");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'BigD','Biology', 'Undergrad', 'genetics');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'BigD','Psychology','Undergrad', null);");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'ZiggyP','Astronomy', 'Graduate', 'black holes');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'Jane88','Biology', 'High School', 'biology 102');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'Jane88','History', 'High School', 'american history');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'JavaLady','Computer Science', null, null);");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'JavaLady','Chemistry', 'Undergrad', 'organic chemistry 302');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'JavaLady','History', 'Undergrad', 'world war II');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'CatzFan','Computer Science', null, null);");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'CatzFan','Math', 'High School', 'trigonometry');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'CatzFan','History', 'High School', 'world history');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MeNeedStudy','Computer Science', null, null);");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MeNeedStudy','Math', 'Undergrad', 'differential equations');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MeNeedStudy','Chemistry', 'Undergrad', 'physical chemistry');");

    }

    /**
     * fills the "friend" table with the hardcoded values for the logged in user(only one user)
     */
    public void fillFriendTable () {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'CatzFan');");
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'Jane88');");
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'JavaLady');");
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'DrStudy');");
    }
    //</editor-fold>

    //<editor-fold desc="Interest Table Methods">
    /**
     * returns a cursor with the search data for a single user from the "interest" table
     * @param user
     * @return
     */
    public Cursor getInterests(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(INTEREST_TABLE_NAME,INTEREST_COLUMN_ALL,
                INTEREST_COLUMN_USER_NAME + " = ?",
                new String[]{user},
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }


    /**
     *
     * @param user
     * @param interests
     * @return
     */
    public Boolean checkInterests(String user,CheckInterest interests) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> interestList = new ArrayList<>(interests.getInterestArrayList());

        Log.d(TAG_HELPER, "This is the number of arguments: " + interestList.size());
        Log.d(TAG_HELPER, "This is the loggedIn value: " + user);
        Log.d(TAG_HELPER, "This is the class value: " + interests.getMyClass());

        String[] arguments = new String[interestList.size() + 1];
        arguments[0] = user;
        String columns = INTEREST_COLUMN_USER_NAME+ " = ? AND ";

        for (int i = 1; i < arguments.length; i++) {

            arguments[i] = interestList.get(i -1);
            if (i < interestList.size()) {
                columns = columns + INTEREST_COLUMN_ALL[i + 1] + " = ? AND ";
            } else {
                columns = columns + INTEREST_COLUMN_ALL[i + 1] + " = ?";
            }
        }

        Cursor cursor = db.query(INTEREST_TABLE_NAME,INTEREST_COLUMN_ALL,
                columns,
                arguments,
                null,
                null,
                null);
        cursor.moveToFirst();

        return (cursor.getCount() ==0);
    }

    /**
     * deletes a interest from the logged in user. Works for interests that contain one or all
     * of the following; subject, level, class.

     * @param deleteInterest
     * @param loggedIn
     */
    public void deleteInterest(CheckInterest deleteInterest, String loggedIn){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> interestArray = deleteInterest.getInterestArrayList();
        String deleteString = "DELETE FROM " + INTEREST_TABLE_NAME + " WHERE " + INTEREST_COLUMN_USER_NAME +
                 "= '" + loggedIn+ "' AND " + INTEREST_COLUMN_SUBJECT + " = '" + deleteInterest.getSubject();

        for (int i = 1; i < interestArray.size(); i++) {
            if (i < interestArray.size() - 1) {
                deleteString = deleteString + "' AND " + INTEREST_COLUMN_ALL[i + 2] + " = '" + interestArray.get(i);
            } else {
                deleteString = deleteString + "' AND " + INTEREST_COLUMN_ALL[i + 2] + " = '" + interestArray.get(i) + "';";
            }
        }

       db.execSQL(deleteString);
    }


    public boolean addInterest(CheckInterest interest, String loggedIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> interestList = new ArrayList<>(interest.getInterestArrayList());
        boolean isUnique;
        isUnique = checkInterests(loggedIn,interest);

        if (interestList.size() == 1) {
            if (isUnique) {
                db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'" + loggedIn + "','" +
                        interest.getSubject() + "', null, null);");
            }
        } else if (interestList.size() == 2) {
            if (isUnique) {
                db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'" + loggedIn + "','" +
                        interest.getSubject() + "','" + interest.getLevel() + "', null);");
            }
        } else {
            if (isUnique) {
                db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'" + loggedIn + "','"
                        + interest.getSubject() + "','" + interest.getLevel() + "','" + interest.getMyClass() + "');");
            }
        }

       return isUnique;
    }

    /**
     *
     * @param interests
     * @param loggedIn
     * @return
     */
    public Cursor searchInterest(CheckInterest interests, String loggedIn) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> interestList = new ArrayList<>(interests.getInterestArrayList());

        Log.d(TAG_HELPER, "This is the number of arguments: " + interestList.size());

        String[] arguments = new String[interestList.size() + 1];
        arguments[0] = loggedIn;
        String columns = INTEREST_COLUMN_USER_NAME+ " <> ? AND ";

        for (int i = 1; i < (arguments.length); i++) {
            arguments[i] = interestList.get(i -1);
            if (i < interestList.size()) {
                columns = columns + INTEREST_COLUMN_ALL[i + 1] + " = ? AND ";
            } else {
                columns = columns + INTEREST_COLUMN_ALL[i + 1] + " = ?";
            }
        }

        Cursor cursor = db.query(INTEREST_TABLE_NAME,INTEREST_COLUMN_ALL,
                columns,
                arguments,
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }
    //</editor-fold>

    //<editor-fold desc="Friend Table Methods">
    /**
     * returns the results from the "friends" table for a specific user
     * @param user
     * @return
     */
    public Cursor getFriends (String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(FRIEND_TABLE_NAME,FRIEND_COLUMN_ALL,
                FRIEND_COLUMN_USERNAME + " = ? ",
                new String[]{user},
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * returns a cursor that is empty if the logged in use and the checked user are not friends.
     * @param loggedIn
     * @param user
     * @return
     */
    public Cursor checkFriends (String loggedIn, String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(FRIEND_TABLE_NAME,FRIEND_COLUMN_ALL,
                FRIEND_COLUMN_USERNAME + " = ? AND " + FRIEND_COLUMN_BUDDY + " = ? ",
                new String[] {loggedIn,user},
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }

    /**
     * adds a user to the "friend" table for the logged in user
     * @param loggedIn
     * @param user
     */
    public void addFriend (String loggedIn, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'" + loggedIn + "', '" + user + "');");
    }

    /**
     * removes a user from the friend data of the logged in user
     * @param loggedIn
     * @param user
     */
    public void removeFriend(String loggedIn, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + FRIEND_TABLE_NAME + " WHERE " + FRIEND_COLUMN_BUDDY+ " = '" +
        user+ "' AND "+ FRIEND_COLUMN_USERNAME+ " = '" + loggedIn+ "';");
    }
    //</editor-fold>

}
