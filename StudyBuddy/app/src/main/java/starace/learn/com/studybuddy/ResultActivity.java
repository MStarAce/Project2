package starace.learn.com.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/10/16.
 */
public class ResultActivity extends AppCompatActivity {
    TextView searchResultInfo;
    ListView resultList;
    ResultAdapter resultAdapter;
    ArrayList<Buddy> memberArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
        populateList();
        getSearchCriteria();


    }

    public void initialize() {
        searchResultInfo = (TextView) findViewById(R.id.result_search_info);
        resultList = (ListView) findViewById(R.id.result_list_view);

    }

    public void getSearchCriteria() {
        Intent getData = getIntent();

        if (getData != null && getData.getStringExtra(SearchActivity.TYPE_SEARCH).equals(SearchActivity.BUDDY_SEARCH)) {
            buddySearch();
        } else if (getData != null) {
            criteriaSearch();
        }

    }

    public void buddySearch(){

    }

    public void criteriaSearch() {

    }

    public void populateList() {
        

    }

}
