package starace.learn.com.studybuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/10/16.
 */
public class ResultActivity extends AppCompatActivity {
    private final static String TAG_RESULT = "ResultActivity";
    public static String SEND_USERNAME = "send_username";
    TextView searchResultInfo;
    ListView resultList;
    ResultCursorAdapter resultCursorAdapter;
    String[] searchCriteria;
    BuddySQLHelper db = BuddySQLHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initialize();
        setOnItemClickListener();
        setBuddyImages();
    }

    public void initialize() {
        searchResultInfo = (TextView) findViewById(R.id.result_search_info);
        resultList = (ListView) findViewById(R.id.result_list_view);

    }

    // determines what serach to complete based on the getExtra from the search activity
    private void search(){
        Intent getSearch = getIntent();
        if (getSearch.getStringExtra(SearchActivity.SEARCH_QUERY) != null) {
            userSearch(getSearch.getStringExtra(SearchActivity.SEARCH_QUERY));
        } else if (getSearch.getStringExtra(SearchActivity.TYPE_SEARCH) != null) {
            if (getSearch.getStringExtra(SearchActivity.TYPE_SEARCH).equals(SearchActivity.BUDDY_SEARCH)) {
                buddySearch();
            } else {
                criteriaSearch();
            }
        }
    }

    // based on who is currently logged in this sets the images in the database to identify friends
    private void setBuddyImages(){
        Cursor cursor = db.getFriends(SearchActivity.loggedIn);
        // check who is on the friend list
        ArrayList<String> userNames = new ArrayList<>();
        while (!cursor.isAfterLast()){
            userNames.add(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.FRIEND_COLUMN_BUDDY)));
            cursor.moveToNext();
        }
        // set buddy image for logged in user, this would need to be handled if new user logs in
        db.addBuddyImage(userNames);
    }

    // single user search from the menu search option of the search activity
    private void userSearch(String user){

        Cursor cursor = db.getUserDataFromList(user);
        resultCursorAdapter = new ResultCursorAdapter(ResultActivity.this,cursor,0);
        resultList.setAdapter(resultCursorAdapter);

        // add search type to the sub title text field
        searchResultInfo.setText("User Search Result");
    }

    // performs a search for all friends of the user
    private void buddySearch(){

        Cursor cursor = db.getFriends(SearchActivity.loggedIn);

        // check who is on the friend list
        ArrayList<String> userNames = new ArrayList<>();
        while (!cursor.isAfterLast()){
            userNames.add(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.FRIEND_COLUMN_BUDDY)));
            cursor.moveToNext();
        }

        // set buddy image for logged in user, this would need to be handled if new user logs in
        //db.addBuddyImage(userNames);

        // get data for each user and add to list
        cursor = db.getUserDataFromList(userNames);
        resultCursorAdapter = new ResultCursorAdapter(ResultActivity.this,cursor,0);
        resultList.setAdapter(resultCursorAdapter);

        // add search type to the sub title text field
        searchResultInfo.setText("Your Buddy List");
    }

    // performs a database search based on the criteria entered by the user on the search activity
    private void criteriaSearch() {
        ArrayList<String> userNames = new ArrayList<>();
        Intent getSearchCriteria = getIntent();
        searchCriteria = getSearchCriteria.getStringExtra(SearchActivity.TYPE_SEARCH).split(",");
        Log.d(TAG_RESULT, "THIS IS THE LENGTH OF THE CRITERIA ARRAY: " + searchCriteria.length + " AT 0: " + searchCriteria[0]);

       if (searchCriteria.length == 1 ) {

           Cursor cursor = db.searchInterest(searchCriteria[0], SearchActivity.loggedIn);
           searchResultInfo.setText(searchCriteria[0]);

           while (!cursor.isAfterLast()){
               userNames.add(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_USER_NAME)));
               cursor.moveToNext();
           }
           cursor.close();

       } else if (searchCriteria.length == 2) {

           Cursor cursor = db.searchInterest(searchCriteria[0], searchCriteria[1], SearchActivity.loggedIn);
           searchResultInfo.setText(searchCriteria[0] + ", " + searchCriteria[1]);
           while (!cursor.isAfterLast()){
               userNames.add(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_USER_NAME)));
               cursor.moveToNext();
           }
           cursor.close();
           Log.d(TAG_RESULT, "USERNAME ARRAYLIST SIZE: " + userNames.size());
       } else if (searchCriteria.length == 3) {

           Cursor cursor = db.searchInterest(searchCriteria[0], searchCriteria[1], searchCriteria[2], SearchActivity.loggedIn);
           searchResultInfo.setText(searchCriteria[0] + ", " + searchCriteria[1] + ", " + searchCriteria[2]);
           while (!cursor.isAfterLast()){
               userNames.add(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_USER_NAME)));
               cursor.moveToNext();
           }
           cursor.close();
       }

        Cursor cursor = db.getUserDataFromList(userNames);
        resultCursorAdapter = new ResultCursorAdapter(ResultActivity.this,cursor,0);
        resultList.setAdapter(resultCursorAdapter);

    }

    private void setOnItemClickListener(){
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent toDetailIntent = new Intent(ResultActivity.this, DetailActivity.class);
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                cursor.moveToPosition(position);
                String passUser = cursor.getString(cursor.getColumnIndex(BuddySQLHelper.BUDDY_COLUMN_USERNAME));
                toDetailIntent.putExtra(SEND_USERNAME,passUser);
                startActivity(toDetailIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        search();
        super.onResume();
    }

}
