package starace.learn.com.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    public static String TYPE_SEARCH = "search_type";
    public static final String  loggedIn = "MeNeedStudy";
    public static String BUDDY_SEARCH = "buddy";

    AutoCompleteTextView editSubject;
    AutoCompleteTextView editLevel;
    EditText editClass;
    ArrayAdapter<String> subjectAdapter;
    ArrayAdapter<String> levelAdapter;

    public static final ArrayList<String> subjectArray = new ArrayList<>(Arrays.asList("History"
            , "Biology","Computer Science","Chemistry","Math","Art History","English"));

    public static final ArrayList<String> levelArray = new ArrayList<>(Arrays.asList("Graduate",
            "Undergrad", "High School", "Technical School"));

    FloatingActionButton searchButton;
    Button buddyButton;

    Intent intentSearchResult;
    Boolean isGoodInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initialize();
        setSearchButton();
        setBuddyButton();
        setDatabase();

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

    public void setSearchButton(){
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
                } else if (editClass.getText().toString().equals("") && editLevel.getText().toString().equals("")){

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

                    searchCase = searchCase +"," + editClass.getText().toString();
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

    public void setBuddyButton () {
        buddyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intentSearchResult.putExtra(TYPE_SEARCH,BUDDY_SEARCH);
                startActivity(intentSearchResult);
            }
        });

    }

    public void setDatabase() {
        BuddySQLHelper createDB = BuddySQLHelper.getInstance(this);
        createDB.fillBuddyTable();
        createDB.fillInterestTable();
        createDB.fillFriendTable();

    }


}
