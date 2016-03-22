package starace.learn.com.studybuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by mstarace on 3/13/16.
 */
public class DetailActivity extends AppCompatActivity {
    private static final String TAG_DETAIL = "DetailActivity";
    FloatingActionButton addBuddy;
    TextView detailTitle;
    ImageView isBuddy;
    ListView detailListView;
    DetailCursorAdapter detailCursorAdapter;
    String userName;
    BuddySQLHelper db = BuddySQLHelper.getInstance(DetailActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();
        setBuddyButton();
        getUserFromResult();
        setInitialState();
        setDetailView();
    }

    private void initialize(){
        addBuddy = (FloatingActionButton) findViewById(R.id.detail_buddy_button);
        detailTitle = (TextView) findViewById(R.id.detail_title_username);
        isBuddy = (ImageView) findViewById(R.id.detail_buddy_true);
        detailListView = (ListView) findViewById(R.id.detail_list_view);
    }
    // sets the buddy button based on the current state
    private void setBuddyButton () {
        addBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBuddy.getAlpha() == 1.0f) {
                    db.removeFriend(SearchActivity.loggedIn,userName);
                    db.removeSingleBuddyImage(userName);
                    isBuddy.setAlpha(0.2f);
                } else {
                    db.addFriend(SearchActivity.loggedIn,userName);
                    db.addSingleBuddyImage(userName);
                    isBuddy.setAlpha(1.0f);
                }

            }
        });

    }

    private void getUserFromResult() {
        Intent fromResult = getIntent();
        userName = fromResult.getStringExtra(ResultActivity.SEND_USERNAME);
    }

    // sets the initial state of buddy image based on a database search for friends
    private void setInitialState (){
        detailTitle.setText(userName);

        Cursor cursor = db.checkFriends(SearchActivity.loggedIn,userName);

        if (cursor.getCount() != 0) {
            isBuddy.setAlpha(1.0f);
        } else {
            isBuddy.setAlpha(0.2f);
        }

        cursor.close();
    }

    private void setDetailView(){
        Cursor cursor = db.getInterests(userName);

        detailCursorAdapter = new DetailCursorAdapter(DetailActivity.this, cursor, 0);
        detailListView.setAdapter(detailCursorAdapter);

    }

}
