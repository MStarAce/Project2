package starace.learn.com.studybuddy;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/22/16.
 */

public class BuddyHelperTester extends AndroidTestCase {

    private BuddySQLHelper db;

    @Override
    public void setUp() throws Exception {

        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = BuddySQLHelper.getInstance(context);
        db.fillInterestTable();
        db.fillFriendTable();
        db.fillBuddyTable();
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    @Test
    public void testDB1(){
        ArrayList<String> knownUsers = new ArrayList<>();
        knownUsers.add("MrPerfect");
        knownUsers.add("DrStudy");
        knownUsers.add("BigD");
        knownUsers.add("ZiggyP");
        knownUsers.add("Jane88");
        knownUsers.add("JavaLady");
        knownUsers.add("CatzFan");

        Cursor cursor = db.getUserDataFromList(knownUsers);
        assertEquals(7,cursor.getInt(cursor.getColumnIndex(BuddySQLHelper.BUDDY_COLUMN_ISBUDDY)));
    }

}
