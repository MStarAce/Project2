package starace.learn.com.studybuddy;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    public static String TYPE_SEARCH = "search_type";
    public static String BUDDY_SEARCH = "buddy";

    EditText editSubject;
    EditText editLevel;
    EditText editClass;
    EditText editDistance;

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

    }

    public void initialize(){
        editSubject = (EditText) findViewById(R.id.search_edit_subject);
        editLevel = (EditText) findViewById(R.id.search_edit_level);
        editClass = (EditText) findViewById(R.id.search_edit_class);
        editDistance = (EditText) findViewById(R.id.search_edit_distance);
        searchButton = (FloatingActionButton) findViewById(R.id.search_button);
        buddyButton = (Button) findViewById(R.id.buddy_button);
        intentSearchResult = new Intent(SearchActivity.this,ResultActivity.class);
    }

    public void setSearchButton(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchCase = "";
                isGoodInput =true;

                if (!editSubject.toString().equals("")) {
                    searchCase = editSubject.getText().toString();
                } else {
                    Toast.makeText(SearchActivity.this,"A \"Subject\" needs to be included to complete a search",
                            Toast.LENGTH_LONG).show();
                    isGoodInput = false;
                }

                if (!editDistance.toString().equals("")) {
                        String testDistance;
                    try {
                        testDistance =  editDistance.getText().toString().trim();
                        Integer.valueOf(testDistance);
                    }
                    catch (Exception e) {
                        Toast.makeText(SearchActivity.this,"For \"Distance\" please use number between 0 and 50",
                                Toast.LENGTH_LONG).show();
                        isGoodInput = false;
                    }
                    searchCase = searchCase + "," + editDistance.getText().toString().trim();

                } else {
                    Toast.makeText(SearchActivity.this,"A \"Distance\" needs to be included to complete a search",
                            Toast.LENGTH_LONG).show();
                    isGoodInput = false;
                }

                if (!editLevel.getText().toString().equals("")) {
                    searchCase = searchCase + "," + editLevel.getText().toString();
                }

                if (!editClass.getText().toString().equals("") && editLevel.getText().toString().equals("")) {
                    Toast.makeText(SearchActivity.this,"Please specify the \"Level\" of your class",
                            Toast.LENGTH_LONG).show();
                    isGoodInput = false;

                } else if (!editClass.getText().toString().equals("")) {
                    searchCase = searchCase + "," + editClass.getText().toString();
                }

                if (isGoodInput) {
                    intentSearchResult.putExtra(TYPE_SEARCH, searchCase.toLowerCase());
                    startActivity(intentSearchResult);
                    editLevel.equals("");
                    editDistance.equals("");
                    editSubject.equals("");
                    editClass.equals("");

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
}
