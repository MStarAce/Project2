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
 * Performs the searches (userName, Buddy, Criteria) with user input from the SearchActivity
 * displays the results and makes them clickable to go to the DetailActivity
 */
public class ResultActivity extends AppCompatActivity {
    private final static String TAG_RESULT = "ResultActivity";
    public static String SEND_USERNAME = "send_username";
    TextView searchResultInfo;
    ListView resultList;
    ResultCursorAdapter resultCursorAdapter;
    BuddySQLHelper db = BuddySQLHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initialize();
        setOnItemClickListener();
        setBuddyImages();
    }

    /**
     * initializes the views in the ResultActivity
     */
    public void initialize() {
        searchResultInfo = (TextView) findViewById(R.id.result_search_info);
        resultList = (ListView) findViewById(R.id.result_list_view);
    }

    /**
     * receives intent from the SearchActivity and determines what search method
     * to execute
     */
    private void search(){
        Intent getSearch = getIntent();
        if (getSearch.getStringExtra(SearchActivity.SEARCH_QUERY) != null) {
            userSearch(getSearch.getStringExtra(SearchActivity.SEARCH_QUERY));
        } else if (getSearch.getStringExtra(SearchActivity.TYPE_SEARCH).equals(SearchActivity.BUDDY_SEARCH)) {
            buddySearch();
        } else if (getSearch.getStringExtra(SearchActivity.TYPE_SEARCH).equals(SearchActivity.CRITERIA_SEARCH)) {
            criteriaSearch();
        }
    }

    /**
     * populates the isBuddy column of the buddy table with images if
     * users are Friends of the loggedIn user
     */
    private void setBuddyImages(){
        Cursor cursor = db.getFriends(SearchActivity.loggedIn);
        ArrayList<String> userNames;
        userNames = cursorToArrayList(cursor,BuddySQLHelper.FRIEND_COLUMN_BUDDY);
        db.addBuddyImage(userNames);
    }

    /**
     * performs a search for an individual user
     * @param user
     */
    private void userSearch(String user){
        Cursor cursor = db.getUserDataFromList(user);
        resultCursorAdapter = new ResultCursorAdapter(ResultActivity.this,cursor,0);
        resultList.setAdapter(resultCursorAdapter);
        searchResultInfo.setText(getResources().getString(R.string.user_search_result));
    }

    /**
     * gets all the userNames of friends of the loggedIn user
     */
    private void buddySearch(){
        Cursor cursor = db.getFriends(SearchActivity.loggedIn);
        ArrayList<String> userNames;
        userNames = cursorToArrayList(cursor,BuddySQLHelper.FRIEND_COLUMN_USERNAME);
        cursor = db.getUserDataFromList(userNames);
        resultCursorAdapter = new ResultCursorAdapter(ResultActivity.this,cursor,0);
        resultList.setAdapter(resultCursorAdapter);
        searchResultInfo.setText(getResources().getString(R.string.buddy_list_search));
    }

    /**
     * performs a search of interests using the inputs entered in the SearchActivity
     */
    private void criteriaSearch() {
        ArrayList<String> userNames;
        Intent getSearchCriteria = getIntent();
        CheckInterest searchCriteria = getSearchCriteria.getParcelableExtra(SearchActivity.INTEREST_CHECK);

        Log.d(TAG_RESULT, "this is the number of inputs from the Parcelabel: " + searchCriteria.getInterestArrayList().size());
        Log.d(TAG_RESULT, "this is the subject from the Parcelabel: " + searchCriteria.getSubject());

        Cursor cursor = db.searchInterest(searchCriteria, SearchActivity.loggedIn);
        String infoString = searchCriteria.getSubject();
        ArrayList<String> interestArray = searchCriteria.getInterestArrayList();

        for (int i = 1; i < interestArray.size(); i++) {
            infoString = infoString + ", " + interestArray.get(i);
        }

        searchResultInfo.setText(infoString);
        userNames = cursorToArrayList(cursor,BuddySQLHelper.INTEREST_COLUMN_USER_NAME);
        cursor.close();
        cursor = db.getUserDataFromList(userNames);
        resultCursorAdapter = new ResultCursorAdapter(ResultActivity.this,cursor,0);
        resultList.setAdapter(resultCursorAdapter);
    }

    /**
     * Takes a cusor and database table column and returns an ArrayList of
     * the values
     *
     * @param cursor
     * @param columnName
     * @return
     */
    private ArrayList<String> cursorToArrayList (Cursor cursor, String columnName){
        ArrayList<String> fromCursor = new ArrayList<>();

        while (!cursor.isAfterLast()){
            fromCursor.add(cursor.getString(cursor.getColumnIndex(columnName)));
            cursor.moveToNext();
        }
        return fromCursor;
    }

    /**
     * set the onItemClickListener for the listView using a cursor adaptor
     */
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

    /**
     * the search method is called from onResume to enable activity refreshing on back navigation
     */
    @Override
    protected void onResume() {
        search();
        super.onResume();
    }

}
