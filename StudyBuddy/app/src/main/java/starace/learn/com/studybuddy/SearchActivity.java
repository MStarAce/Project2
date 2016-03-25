package starace.learn.com.studybuddy;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * SearchActivity records and passes user inputs for three types of searches
 * (Buddy Search, Username search, Criteria search)
 */
public class SearchActivity extends AppCompatActivity {
    private static final String MY_PREF_FILE = "myPrefFile";
    private static final String TAG_SEARCH = "SearchActivity";
    public static final String TYPE_SEARCH = "search_type";
    public static final String loggedIn = "MeNeedStudy";
    public static final String BUDDY_SEARCH = "buddy";
    public static final String CRITERIA_SEARCH = "criteria";
    public static final String INTEREST_CHECK = "interest_check";
    public static final String HAS_RUN = "hasRun";
    public static final String SEARCH_QUERY = "searchQuery";
    public ArrayList<String> subjectArray;
    public ArrayList<String> levelArray;

    AutoCompleteTextView editSubject;
    AutoCompleteTextView editLevel;
    EditText editClass;
    ArrayAdapter<String> subjectAdapter;
    ArrayAdapter<String> levelAdapter;
    FloatingActionButton searchButton;
    Button buddyButton;
    Boolean hasRun;
    Intent intentSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        checkPreferences();
        initializeViews();
        initializeAdapters();
        setSearchButton();
        setBuddyButton();
        handleIntent(getIntent());
    }

    /**
     * check to see if database table have already been created
     * creates them if they haven't been
     */
    public void checkPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_FILE, MODE_PRIVATE);
        hasRun = sharedPreferences.getBoolean(HAS_RUN, false);
        if (!hasRun) {
            setDatabase();
        }
    }

    /**
     * initializes the views for the SearchActivity
     */
    public void initializeViews() {
        levelArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.level_array)));
        subjectArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.subject_array)));
        editSubject = (AutoCompleteTextView) findViewById(R.id.search_edit_subject);
        editLevel = (AutoCompleteTextView) findViewById(R.id.search_edit_level);
        editClass = (EditText) findViewById(R.id.search_edit_class);
        searchButton = (FloatingActionButton) findViewById(R.id.search_button);
        buddyButton = (Button) findViewById(R.id.buddy_button);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * initializes the adapters, result intent, and sets the adapters for the autofilltextviews
     */
    public void initializeAdapters() {
        intentSearchResult = new Intent(SearchActivity.this, ResultActivity.class);
        subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, subjectArray);
        editSubject.setAdapter(subjectAdapter);
        levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, levelArray);
        editLevel.setAdapter(levelAdapter);
    }

    /**
     * sets the onclick listener for the FAB button to start the criteria search
     * in the ResultActivity
     */
    private void setSearchButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckInterest checkInterest = CheckInterest.checkInput(editSubject, editLevel, editClass, subjectArray, levelArray);
                Log.d(TAG_SEARCH,"This is the subject returned from checkInterest: " + checkInterest.getSubject());
                if (checkInterest.getMessage() != null) {
                    Toast.makeText(SearchActivity.this, checkInterest.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    intentSearchResult.putExtra(TYPE_SEARCH, CRITERIA_SEARCH);
                    intentSearchResult.putExtra(INTEREST_CHECK, checkInterest);
                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    startActivity(intentSearchResult);
                }
            }
        });
    }

    /**
     * sets the onclickListener for the buddy search button
     * passes the search type to the ResultActivity
     */
    private void setBuddyButton() {
        buddyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSearchResult.putExtra(TYPE_SEARCH, BUDDY_SEARCH);
                startActivity(intentSearchResult);
            }
        });
    }

    /**
     * fills the database tables calling methods from the SQLiteOpenHelper
     */
    private void setDatabase() {
        BuddySQLHelper createDB = BuddySQLHelper.getInstance(this);
        createDB.fillBuddyTable();
        createDB.fillInterestTable();
        createDB.fillFriendTable();
    }

    /**
     * onDestroy overridden to save sharedpreferences, a boolean to indicate
     * if tables have been already filled
     */
    @Override
    protected void onDestroy() {
        SharedPreferences.Editor sharedPref = getSharedPreferences(MY_PREF_FILE, MODE_PRIVATE).edit().putBoolean(HAS_RUN, true);
        sharedPref.commit();
        super.onDestroy();
    }

    /**
     * Option Menu contains two buttons,
     * a search button and an edit button,
     * search button captures a user input to send to the ResultActivity
     * edit button starts the EditInterest activity
     * @param menu
     * @return
     */
    //<editor-fold desc="Option Menu">
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("User Name Search (case sensitive");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Intent toEditInterest = new Intent(SearchActivity.this, EditInterests.class);
            startActivity(toEditInterest);
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Intent toEditInterest = new Intent(SearchActivity.this, ResultActivity.class);
            toEditInterest.putExtra(SEARCH_QUERY, query);
            startActivity(toEditInterest);
        }
    }
    //</editor-fold>
}
