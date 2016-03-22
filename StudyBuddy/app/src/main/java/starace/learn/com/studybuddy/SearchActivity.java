package starace.learn.com.studybuddy;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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

public class SearchActivity extends AppCompatActivity {
    private static final String MY_PREF_FILE = "myPrefFile";
    private static final String TAG_SEARCH = "SearchActivity";
    public static final String TYPE_SEARCH = "search_type";
    public static final String  loggedIn = "MeNeedStudy";
    public static final String BUDDY_SEARCH = "buddy";
    public static final String HAS_RUN = "hasRun";
    public static final String SEARCH_QUERY = "searchQuery";

    AutoCompleteTextView editSubject;
    AutoCompleteTextView editLevel;
    EditText editClass;
    ArrayAdapter<String> subjectAdapter;
    ArrayAdapter<String> levelAdapter;

    private static final ArrayList<String> subjectArray = new ArrayList<>(Arrays.asList("History"
            , "Biology","Computer Science","Chemistry","Math","Art History","English"));

    private static final ArrayList<String> levelArray = new ArrayList<>(Arrays.asList("Graduate",
            "Undergrad", "High School", "Technical School"));

    FloatingActionButton searchButton;
    Button buddyButton;
    Boolean hasRun;
    Intent intentSearchResult;
    Boolean isGoodInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initialize();
        setSearchButton();
        setBuddyButton();

        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREF_FILE,MODE_PRIVATE);
        hasRun = sharedPreferences.getBoolean(HAS_RUN,false);
        if (!hasRun) {
            setDatabase();

        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        handleIntent(getIntent());
    }

    public void initialize(){
        editSubject = (AutoCompleteTextView) findViewById(R.id.search_edit_subject);
        editLevel = (AutoCompleteTextView) findViewById(R.id.search_edit_level);
        editClass = (EditText) findViewById(R.id.search_edit_class);
        searchButton = (FloatingActionButton) findViewById(R.id.search_button);
        buddyButton = (Button) findViewById(R.id.buddy_button);
        intentSearchResult = new Intent(SearchActivity.this,ResultActivity.class);
        subjectAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,subjectArray);
        editSubject.setAdapter(subjectAdapter);
        levelAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,levelArray);
        editLevel.setAdapter(levelAdapter);
    }

    // sets the search button and passes the edittext values to the result activity
    private void setSearchButton(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchCase = "";
                isGoodInput = true;

                //check to see if subject is valid
                if (subjectArray.contains(editSubject.getText().toString())) {
                    searchCase = editSubject.getText().toString();
                } else {
                    // might want to use and AlertDialogue here to give option to request new subject
                    Toast.makeText(SearchActivity.this, "Please choose a \"Subject\" from the list " +
                            "to complete a search", Toast.LENGTH_LONG).show();
                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    isGoodInput = false;
                }

                //check to see if level is valid
                if (!editClass.getText().toString().equals("") && editLevel.getText().toString().equals("")) {
                    Toast.makeText(SearchActivity.this, "Please specify the \"Level\" of your \"class\"",
                            Toast.LENGTH_LONG).show();
                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    isGoodInput = false;

                } else if (editClass.getText().toString().equals("") && levelArray.contains(editLevel.getText().toString())) {
                    searchCase = searchCase + "," + editLevel.getText().toString();
                } else if (editClass.getText().toString().equals("") && editLevel.getText().toString().equals("")) {

                } else {
                    Toast.makeText(SearchActivity.this, "Please choose a \"Level\" from the list to complete the search",
                            Toast.LENGTH_LONG).show();
                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    isGoodInput = false;
                }
                //check to see if class exists
                if (!editClass.getText().toString().equals("")) {

                    searchCase = searchCase + "," + editClass.getText().toString();
                }

                if (isGoodInput) {
                    intentSearchResult.putExtra(TYPE_SEARCH, searchCase);
                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    startActivity(intentSearchResult);

                }

            }
        });

    }

    private void setBuddyButton () {
        buddyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentSearchResult.putExtra(TYPE_SEARCH, BUDDY_SEARCH);
                startActivity(intentSearchResult);
            }
        });

    }

    private void setDatabase() {
        BuddySQLHelper createDB = BuddySQLHelper.getInstance(this);
        createDB.fillBuddyTable();
        createDB.fillInterestTable();
        createDB.fillFriendTable();

    }

    @Override
    protected void onDestroy() {

        SharedPreferences.Editor sharedPref = getSharedPreferences(MY_PREF_FILE,MODE_PRIVATE).edit().putBoolean(HAS_RUN, true);
        sharedPref.commit();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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

    // handles the search intent
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Intent toEditInterest = new Intent(SearchActivity.this, ResultActivity.class);
            toEditInterest.putExtra(SEARCH_QUERY, query);
            startActivity(toEditInterest);
        }
    }
}
