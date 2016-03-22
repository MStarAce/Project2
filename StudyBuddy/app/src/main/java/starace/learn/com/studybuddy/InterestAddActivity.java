package starace.learn.com.studybuddy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class InterestAddActivity extends AppCompatActivity {
    private static final String TAG_ADD = "add_interest_activity";

    AutoCompleteTextView editSubject;
    AutoCompleteTextView editLevel;
    EditText editClass;
    ArrayAdapter<String> subjectAdapter;
    ArrayAdapter<String> levelAdapter;

    private static final ArrayList<String> subjectArray = new ArrayList<>(Arrays.asList("History"
            , "Biology","Computer Science","Chemistry","Math","Art History","English"));

    private static final ArrayList<String> levelArray = new ArrayList<>(Arrays.asList("Graduate",
            "Undergrad", "High School", "Technical School"));

    FloatingActionButton addButton;
    Boolean isGoodInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interest);

        initialize();
        setAddButton();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void initialize(){
        editSubject = (AutoCompleteTextView) findViewById(R.id.interest_add_subject);
        editLevel = (AutoCompleteTextView) findViewById(R.id.interest_add_level);
        editClass = (EditText) findViewById(R.id.interest_add_class);
        addButton = (FloatingActionButton) findViewById(R.id.interest_add_button);
        subjectAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,subjectArray);
        editSubject.setAdapter(subjectAdapter);
        levelAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,levelArray);
        editLevel.setAdapter(levelAdapter);
    }

    // set the add button and checks that the user input is valid and unique
    private void setAddButton(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchCase = "";
                isGoodInput = true;
                boolean isUnique = false;

                //check to see if subject is valid
                if (subjectArray.contains(editSubject.getText().toString())) {
                    searchCase = editSubject.getText().toString();
                } else {
                    // might want to use and AlertDialogue here to give option to request new subject
                    Toast.makeText(InterestAddActivity.this, "Please choose a \"Subject\" from the list " +
                            "to complete a search", Toast.LENGTH_LONG).show();
                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    isGoodInput = false;
                }

                //check to see if level is valid
                if (!editClass.getText().toString().equals("") && editLevel.getText().toString().equals("")) {
                    Toast.makeText(InterestAddActivity.this, "Please specify the \"Level\" of your \"class\"",
                            Toast.LENGTH_LONG).show();
                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    isGoodInput = false;

                } else if (editClass.getText().toString().equals("") && levelArray.contains(editLevel.getText().toString())) {
                    searchCase = searchCase + "," + editLevel.getText().toString();
                } else if (editClass.getText().toString().equals("") && editLevel.getText().toString().equals("")) {

                } else {
                    Toast.makeText(InterestAddActivity.this, "Please choose a \"Level\" from the list to complete the search",
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

                    BuddySQLHelper db = BuddySQLHelper.getInstance(InterestAddActivity.this);

                    isUnique = db.addInterest(searchCase,SearchActivity.loggedIn);

                    Log.d(TAG_ADD, "THE BOOLEAN IS : " + isUnique);

                    if (!isUnique){
                        Toast.makeText(InterestAddActivity.this,"You already have an interest matching that description.",Toast.LENGTH_LONG).show();
                    }

                    editLevel.setText("");
                    editSubject.setText("");
                    editClass.setText("");
                    finish();

                }

            }
        });

    }

}
