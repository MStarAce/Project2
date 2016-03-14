package starace.learn.com.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/13/16.
 */
public class DetailActivity extends AppCompatActivity {
    FloatingActionButton addBuddy;
    TextView detailTitle;
    ImageView isBuddy;
    ListView detailListView;

    DetailAdapter detailAdapter;

    ArrayList<Interest> curInterest;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();
        setBuddyButton();
        getDataFromResult();
        setInitialState();
        initializeDetailAdapter();
    }

    public void initialize(){
        addBuddy = (FloatingActionButton) findViewById(R.id.detail_buddy_button);
        detailTitle = (TextView) findViewById(R.id.detail_title_username);
        isBuddy = (ImageView) findViewById(R.id.detail_buddy_true);
        detailListView = (ListView) findViewById(R.id.detail_list_view);
        curInterest = new ArrayList<>();

    }

    public void setBuddyButton () {
        addBuddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buddyIndex;
                if (ResultActivity.myBuddyList.contains(userName)) {
                    buddyIndex = ResultActivity.myBuddyList.indexOf(userName);
                    ResultActivity.myBuddyList.remove(buddyIndex);

                    isBuddy.setAlpha(0.2f);

                } else {
                    ResultActivity.myBuddyList.add(userName);
                    isBuddy.setAlpha(1.0f);

                }

            }
        });

    }

    public void getDataFromResult() {
        Intent fromResult = getIntent();
        curInterest = fromResult.getParcelableArrayListExtra(ResultActivity.SEND_DETAIL);
        userName = fromResult.getStringExtra(ResultActivity.SEND_USERNAME);
    }

    public void initializeDetailAdapter() {

        detailAdapter = new DetailAdapter(this,curInterest);
        detailListView.setAdapter(detailAdapter);

    }

    public void setInitialState (){
        detailTitle.setText(userName);

        if (ResultActivity.myBuddyList.contains(userName)) {
            isBuddy.setAlpha(1.0f);

        } else {
            isBuddy.setAlpha(0.2f);

        }


    }


}
