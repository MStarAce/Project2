package starace.learn.com.studybuddy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * InterestAddActivity allows user to add a new interest.
 * checks for unique input and adds unique, valid entries
 */
public class InterestAddActivity extends AppCompatActivity {
    private static final String TAG_ADD = "add_interest_activity";
    public ArrayList<String> subjectArray;
    public ArrayList<String> levelArray;

    AutoCompleteTextView editSubject;
    AutoCompleteTextView editLevel;
    EditText editClass;
    ArrayAdapter<String> subjectAdapter;
    ArrayAdapter<String> levelAdapter;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interest);
        initialize();
        setAddButton();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initialize(){
        subjectArray = new ArrayList<> (Arrays.asList(getResources().getStringArray(R.array.subject_array)));
        levelArray = new ArrayList<> (Arrays.asList(getResources().getStringArray(R.array.level_array)));
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
                boolean isUnique;
                CheckInterest checkInterest = CheckInterest.checkInput(editSubject, editLevel, editClass, subjectArray, levelArray);

                if (checkInterest.getMessage()!=null) {
                    Toast.makeText(InterestAddActivity.this,checkInterest.getMessage(),Toast.LENGTH_LONG).show();
                } else {

                    BuddySQLHelper db = BuddySQLHelper.getInstance(InterestAddActivity.this);
                    isUnique = db.addInterest(checkInterest,SearchActivity.loggedIn);

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
