package starace.learn.com.studybuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/15/16.
 */
public class BuddySQLHelper extends SQLiteOpenHelper {
    private static final String TAG_HELPER = "BuddySQLHelper";
    private static final  int DB__BUDDY_VERSION = 1;
    public static final String DB_BUDDY_NAME = "buddy_db";
    public static final String BUDDY_TABLE_NAME = "buddy";
    public static final String INTEREST_TABLE_NAME = "interest";

    //String userName, String firstName, String lastName, String city, String state, int distance
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

    public static final String INTEREST_COLUMN_ID = "_id";
    public static final String INTEREST_COLUMN_USER_NAME = "user_name";
    public static final String INTEREST_COLUMN_SUBJECT = "subject";
    public static final String INTEREST_COLUMN_LEVEL = "level";
    public static final String INTEREST_COLUMN_CLASS = "class";
    public static final String[] INTEREST_COLUMN_ALL = {INTEREST_COLUMN_ID,INTEREST_COLUMN_USER_NAME,INTEREST_COLUMN_SUBJECT,
            INTEREST_COLUMN_LEVEL, INTEREST_COLUMN_CLASS};

    public static final int IMAGE_BUDDY = R.drawable.buddy;
    public static final int IMAGE_NO = R.drawable.no_image;
    public static final int IMAGE_USER1 = R.drawable.rface1;
    public static final int IMAGE_USER2 = R.drawable.babyface;
    public static final int IMAGE_USER3 = R.drawable.ouch;
    public static final int IMAGE_USER4 = R.drawable.prez;
    public static final int IMAGE_USER5 = R.drawable.sun;
    public static final int IMAGE_USER6 = R.drawable.tips;
    public static final int IMAGE_USER7 = R.drawable.face2;
    public static final int IMAGE_USER8 = R.drawable.bad;


    public static final String TABLE_INTEREST_CREATE = "CREATE TABLE interest (" +INTEREST_COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + INTEREST_COLUMN_USER_NAME+ " TEXT NOT NULL, " +
            INTEREST_COLUMN_SUBJECT + " TEXT, " + INTEREST_COLUMN_LEVEL + " TEXT, " + INTEREST_COLUMN_CLASS +
            " TEXT, FOREIGN KEY " + "(user_name) REFERENCES buddy (_user_name));";

    public static final String TABLE_INTEREST_DROP = "DROP TABLE IF EXISTS interest;";

    public static final String FRIEND_COLUMN_ID = "_id";
    public static final String FRIEND_TABLE_NAME = "friend";
    public static final String FRIEND_COLUMN_USERNAME = "user_name";
    public static final String FRIEND_COLUMN_BUDDY = "buddy";
    public static final String[] FRIEND_COLUMN_ALL = {FRIEND_COLUMN_ID,FRIEND_COLUMN_USERNAME, FRIEND_COLUMN_BUDDY};

    public static final String TABLE_FRIEND_CREATE = "CREATE TABLE friend (" + FRIEND_COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+ FRIEND_COLUMN_USERNAME + " TEXT NOT NULL, " +
            FRIEND_COLUMN_BUDDY + " TEXT NOT NULL);";

    public static final String TABLE_FRIEND_DROP = "DROP TABLE IF EXISTS friend;";

    public BuddySQLHelper(Context context) {
        super(context, DB_BUDDY_NAME, null, DB__BUDDY_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_BUDDY_CREATE);
        db.execSQL(TABLE_INTEREST_CREATE);
        db.execSQL(TABLE_FRIEND_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_BUDDY_DROP);
        db.execSQL(TABLE_INTEREST_DROP);
        db.execSQL(TABLE_FRIEND_DROP);
        this.onCreate(db);

    }

    private static BuddySQLHelper instance;

    public static BuddySQLHelper getInstance(Context context) {
        if (instance == null){
            instance =  new BuddySQLHelper(context.getApplicationContext());
        }

        return instance;
    }

    public Cursor getBuddyCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BUDDY_TABLE_NAME,BUDDY_COLUMN_ALL, null,null,null,null,null);
        return cursor;
    }

    public void addBuddyImage(ArrayList<String> userName) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < userName.size(); i++) {
            db.execSQL("UPDATE buddy SET " + BUDDY_COLUMN_ISBUDDY + " = " +
                    IMAGE_BUDDY + " WHERE " + BUDDY_COLUMN_USERNAME + " = '" + userName.get(i) + "';");
        }
    }

    public void addSingleBuddyImage(String user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE buddy SET " + BUDDY_COLUMN_ISBUDDY + " = " +
                    IMAGE_BUDDY + " WHERE " + BUDDY_COLUMN_USERNAME + " = '" + user + "';");
    }

    public void removeSingleBuddyImage(String user){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE buddy SET " + BUDDY_COLUMN_ISBUDDY + " = " + null + " WHERE " + BUDDY_COLUMN_USERNAME + " = '" +
                user + "';");

    }

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

    public Cursor getInterestCursor(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(INTEREST_TABLE_NAME, INTEREST_COLUMN_ALL, null,null,null,null,null);
        return cursor;
    }

    public void fillInterestTable(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MrPerfect','Biology', 'High School', 'biology 101');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MrPerfect','Chemistry', 'High School', 'chemistry 101');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'DrStudy','Computer Science',null,null);");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'BigD','Biology', 'Undergrad', 'genetics');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'BigD','Psychology','Undergrad', null);");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'ZiggyP','Astronomy', 'Graduate', 'black holes');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'Jane88','Biology', 'High school', 'biology 102');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'Jane88','History', 'High school', 'american history');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'JavaLady','Computer Science', null, null);");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'JavaLady','Chemistry', 'Undergrad', 'organic chemistry 302');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'JavaLady','History', 'Undergrad', 'world war II');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'CatzFan','Computer Science', null, null);");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'CatzFan','Math', 'High school', 'trigonometry');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'CatzFan','History', 'High school', 'world history');");

        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MeNeedStudy','Computer Science', null, null);");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MeNeedStudy','Math', 'Undergrad', 'differential equations');");
        db.execSQL("INSERT INTO " + INTEREST_TABLE_NAME + " VALUES (null,'MeNeedStudy','Chemistry', 'Undergrad', 'physical chemistry');");

    }

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


    public Cursor searchInterest(String subject, String level, String myClass, String loggedIn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(INTEREST_TABLE_NAME,INTEREST_COLUMN_ALL,
                INTEREST_COLUMN_SUBJECT+ " = ? OR " + INTEREST_COLUMN_LEVEL + " = ? OR " +
                        INTEREST_COLUMN_CLASS + " = ? AND NOT " +INTEREST_COLUMN_USER_NAME +" = ? ",
                new String[]{subject,level,myClass,loggedIn},
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }



    public Cursor searchInterest(String subject, String level, String loggedIn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(INTEREST_TABLE_NAME,INTEREST_COLUMN_ALL,
                INTEREST_COLUMN_SUBJECT+ " = ? AND " + INTEREST_COLUMN_LEVEL + " = ? AND NOT "
                        +INTEREST_COLUMN_USER_NAME +" = ? ",
                new String[]{subject,level,loggedIn},
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor searchInterest(String subject, String loggedIn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(INTEREST_TABLE_NAME,INTEREST_COLUMN_ALL,
                INTEREST_COLUMN_SUBJECT+ " = ? AND NOT " + INTEREST_COLUMN_USER_NAME+ " = ? ",
                new String[]{subject,loggedIn},
                null,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }

    public void fillFriendTable () {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'CatzFan');");
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'Jane88');");
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'JavaLady');");
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'MeNeedStudy', 'DrStudy');");
    }

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

    public void addFriend (String loggedIn, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + FRIEND_TABLE_NAME + " VALUES (null,'" + loggedIn + "', '" + user + "');");
    }

    public void removeFriend(String loggedIn, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + FRIEND_TABLE_NAME + " WHERE " + FRIEND_COLUMN_BUDDY+ " = '" +
        user+ "' AND "+ FRIEND_COLUMN_USERNAME+ " = '" + loggedIn+ "';");
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(TABLE_FRIEND_DROP);
        db.execSQL(TABLE_INTEREST_DROP);
        db.execSQL(TABLE_BUDDY_DROP);

    }
}
