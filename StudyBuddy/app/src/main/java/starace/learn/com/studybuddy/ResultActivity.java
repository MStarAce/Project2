package starace.learn.com.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/10/16.
 */
public class ResultActivity extends AppCompatActivity {
    private static String TAG_RESULT = "ResultActivity";
    public static String SEND_DETAIL = "send_detail";
    public static String SEND_USERNAME = "send_username";
    public static ArrayList<String> myBuddyList = new ArrayList<>();
    TextView searchResultInfo;
    ListView resultList;
    ResultAdapter resultAdapter;
    ArrayList<Buddy> memberArrayList;
    ArrayList<Buddy> resultMemberArrayList;
    String[] searchCriteria;
    Intent toDetailIntent;
    Boolean isBuddySearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initialize();
        populateList();
        search();
        setDataToListView();
        setOnItemClickListener();


    }

    public void initialize() {
        searchResultInfo = (TextView) findViewById(R.id.result_search_info);
        resultList = (ListView) findViewById(R.id.result_list_view);
        resultMemberArrayList = new ArrayList<>();
        memberArrayList = new ArrayList<>();
        toDetailIntent = new Intent(ResultActivity.this, DetailActivity.class);

    }

    public void search() {
        Intent getData = getIntent();

        if (getData != null && getData.getStringExtra(SearchActivity.TYPE_SEARCH).equals(SearchActivity.BUDDY_SEARCH)) {
            buddySearch();
            isBuddySearch = true;
        } else if (getData != null) {
            criteriaSearch();
            isBuddySearch = false;
        }

    }

    public void buddySearch(){

        Buddy myselfBuddy = memberArrayList.get(7); // I am using myself to be the person who acts as the one signed in
        ArrayList<String> buddyUserNameList = new ArrayList<>();

        for (String buddyUserName : myselfBuddy.getBuddyListArray()) {
            buddyUserNameList.add(buddyUserName);
        }

        for (int k = 0; k < buddyUserNameList.size(); k++) {
            for (int i = 0; i < memberArrayList.size(); i++) {
                if (memberArrayList.get(i).getUserName().equals(buddyUserNameList.get(k))) {
                    resultMemberArrayList.add(memberArrayList.get(i));
                }

            }
        }

        searchResultInfo.setText("My Buddy List");

    }

    public void criteriaSearch() {
        Intent getSearchCriteria = getIntent();
        searchCriteria = getSearchCriteria.getStringExtra(SearchActivity.TYPE_SEARCH).split(",");

        switch (searchCriteria.length) {
            case 2:

                for (Buddy curBuddy : memberArrayList) {
                    for(int i =0; i < curBuddy.getInterestArray().size(); i++)
                        if (curBuddy.getDistance() < Integer.valueOf(searchCriteria[1]) &&
                                curBuddy.getInterestArray().get(i).getSubject().equals(searchCriteria[0])) {
                            resultMemberArrayList.add(curBuddy);
                        }
                }
                searchResultInfo.setText(searchCriteria[0] + ", " + searchCriteria[1] + "miles");

                break;

            case 3:

                for (Buddy curBuddy : memberArrayList) {
                    for(int i =0; i < curBuddy.getInterestArray().size(); i++)
                        if (curBuddy.getDistance() <= Integer.valueOf(searchCriteria[1]) &&
                                curBuddy.getInterestArray().get(i).getSubject().equals(searchCriteria[0]) &&
                                curBuddy.getInterestArray().get(i).getLevel().equals(searchCriteria[2])) {
                            resultMemberArrayList.add(curBuddy);
                        }
                }
                searchResultInfo.setText(searchCriteria[0] + ", " + searchCriteria[2] + ", " + searchCriteria[1] + "miles");

                break;
            case 4:

                for (Buddy curBuddy : memberArrayList) {
                    for(int i =0; i < curBuddy.getInterestArray().size(); i++)
                        if (curBuddy.getDistance() <= Integer.valueOf(searchCriteria[1]) &&
                                curBuddy.getInterestArray().get(i).getSubject().equals(searchCriteria[0]) &&
                                curBuddy.getInterestArray().get(i).getLevel().equals(searchCriteria[2]) &&
                                curBuddy.getInterestArray().get(i).getMyClass().equals(searchCriteria[3])) {
                            resultMemberArrayList.add(curBuddy);
                        }
                }
                searchResultInfo.setText(searchCriteria[0] + ", " + searchCriteria[2] + ", " +  searchCriteria[3] +
                        ", " + searchCriteria[1] + "miles");

                break;

            default:
                break;
        }



        setDataToListView();

    }

    public void populateList() {

        //String userName, String firstName, String lastName, String city, String state, int distance

        memberArrayList.add(new Buddy("MrPerfect", "Billy", "Bob", "Piedmont", "CA", 3));
        memberArrayList.add(new Buddy("DrStudy", "Gloria", "Sanchez", "Richmond", "CA", 6));
        memberArrayList.add(new Buddy("BigD", "Dave", "Roberts", "San Leandro", "CA", 4));
        memberArrayList.add(new Buddy("ZiggyP", "Zane", "Proctor", "SF", "CA", 2));
        memberArrayList.add(new Buddy("Jane88", "Jane", "Driver", "Oakland", "CA", 0));
        memberArrayList.add(new Buddy("JavaLady", "Sadie", "Serotte", "Mill Valley", "CA", 12));
        memberArrayList.add(new Buddy("CatzFan", "Jesus", "Rodriguez", "Daily City", "CA", 8));
        memberArrayList.add(new Buddy("MeNeedStudy", "Michael", "Starace", "Berkley", "CA", 1));

        // add interests

        memberArrayList.get(0).getInterestArray().add(new Interest("Biology", "high school", "Biology 101"));
        memberArrayList.get(0).getInterestArray().add(new Interest("Chemistry", "high school", "Chemistry 101"));

        Log.d(TAG_RESULT, "check memberArrayList at position 0 " + memberArrayList.get(0));

        memberArrayList.get(1).getInterestArray().add(new Interest("Computer Science"));

        Log.d(TAG_RESULT, "check memberArrayList at position 1 " + memberArrayList.get(1));

        memberArrayList.get(2).getInterestArray().add(new Interest("Biology", "undergrad", "Genetics"));
        memberArrayList.get(2).getInterestArray().add(new Interest("Psychology", "undergrad"));

        Log.d(TAG_RESULT, "check memberArrayList at position 2 " + memberArrayList.get(2));

        memberArrayList.get(3).getInterestArray().add(new Interest("Astronomy", "graduate", "black holes"));

        Log.d(TAG_RESULT, "check memberArrayList at position 3 " + memberArrayList.get(3));

        memberArrayList.get(4).getInterestArray().add(new Interest("Biology", "high school", "Biology 102"));
        memberArrayList.get(4).getInterestArray().add(new Interest("History", "high school", "American History"));

        Log.d(TAG_RESULT, "check memberArrayList at position 4 " + memberArrayList.get(4));

        memberArrayList.get(5).getInterestArray().add(new Interest("Computer Science"));
        memberArrayList.get(5).getInterestArray().add(new Interest("Chemistry", "undergrad", "Organic Chemistry 302"));
        memberArrayList.get(5).getInterestArray().add(new Interest("History", "undergrad", "World War II"));

        Log.d(TAG_RESULT, "check memberArrayList at position 5 " + memberArrayList.get(5));

        memberArrayList.get(6).getInterestArray().add(new Interest("Computer Science"));
        memberArrayList.get(6).getInterestArray().add(new Interest("Math", "high school", "Trigonometry"));
        memberArrayList.get(6).getInterestArray().add(new Interest("History", "high school", "World History"));

        Log.d(TAG_RESULT, "check memberArrayList at position 6 " + memberArrayList.get(6));

        memberArrayList.get(7).getInterestArray().add(new Interest("Computer Science"));
        memberArrayList.get(7).getInterestArray().add(new Interest("Math", "undergrad", "differential equations"));
        memberArrayList.get(7).getInterestArray().add(new Interest("Chemistry", "undergrad", "Physical Chemistry"));
        memberArrayList.get(7).getInterestArray().add(new Interest("Art History", "undergrad", "The art of Buddhism"));

        Log.d(TAG_RESULT, "check memberArrayList at position 7 " + memberArrayList.get(7));

        // add buddy list
        myBuddyList.add(memberArrayList.get(1).getUserName());
        myBuddyList.add(memberArrayList.get(3).getUserName());
        myBuddyList.add(memberArrayList.get(5).getUserName());

        memberArrayList.get(7).getBuddyListArray().add(myBuddyList.get(0));
        memberArrayList.get(7).getBuddyListArray().add(myBuddyList.get(1));
        memberArrayList.get(7).getBuddyListArray().add(myBuddyList.get(2));

    }

    public void setDataToListView() {
        resultAdapter = new ResultAdapter(ResultActivity.this, resultMemberArrayList,R.drawable.buddy);
        resultList.setAdapter(resultAdapter);

    }

    public void setOnItemClickListener(){
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Buddy sendBuddy = resultMemberArrayList.get(position);
                toDetailIntent.putParcelableArrayListExtra(SEND_DETAIL, sendBuddy.getInterestArray());
                toDetailIntent.putExtra(SEND_USERNAME, sendBuddy.getUserName());

                startActivity(toDetailIntent);
            }
        });

    }

    @Override
    protected void onResume() {

        resultAdapter.notifyDataSetChanged();
        super.onResume();
    }
}
