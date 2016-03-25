package starace.learn.com.studybuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * EditInterest Activity displays the loggedIn user's interest and allows them,
 * to be deleted and for new interests to be added
 */

public class EditInterests extends AppCompatActivity {
    private static final String TAG_INTEREST = "EditInterest";
    TextView interestTitle;
    ListView interestListView;
    DetailCursorAdapter interestCursorAdapter;
    BuddySQLHelper db = BuddySQLHelper.getInstance(EditInterests.this);
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_interest_);

        initialize();
        setOnItemLongClick();
        setAddButton();
    }

    private void initialize(){
        add = (FloatingActionButton) findViewById(R.id.interest_add_button);
        interestTitle = (TextView) findViewById(R.id.interest_title_username);
        interestListView = (ListView) findViewById(R.id.interest_list_view);
    }

    private void setInterestView(){
        Cursor cursor = db.getInterests(SearchActivity.loggedIn);
        interestCursorAdapter = new DetailCursorAdapter(EditInterests.this, cursor, 0);
        interestListView.setAdapter(interestCursorAdapter);
    }

    /**
     * long click is used to delete the item at that index
     */
    private void setOnItemLongClick() {
        interestListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getAdapter().getItem(position);
                CheckInterest deleteInterest = new CheckInterest();

                deleteInterest.setSubject(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_SUBJECT)));
                deleteInterest.setLevel(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_LEVEL)));
                deleteInterest.setMyClass(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_CLASS)));

                db.deleteInterest(deleteInterest, SearchActivity.loggedIn);
                setInterestView();
                return false;
            }
        });
    }

    /**
     * starts the InterestAddActivity
     */
    private void setAddButton(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInterestAdd = new Intent(EditInterests.this, InterestAddActivity.class);
                startActivity(toInterestAdd);
            }
        });

    }

    /**
     * set interest is called from on resume to re-populate the activity
     * on back button press
     */
    @Override
    protected void onResume() {
        setInterestView();
        super.onResume();
    }
}
