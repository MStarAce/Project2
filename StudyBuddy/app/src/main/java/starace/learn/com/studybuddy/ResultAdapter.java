package starace.learn.com.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mstarace on 3/10/16.
 */
public class ResultAdapter extends ArrayAdapter {
    List<Buddy> myBuddy = new ArrayList<>();
    int buddy;

    public ResultAdapter(Context context, ArrayList<Buddy> objects, int buddyImage) {
        super(context, -1, objects);
        myBuddy = objects;
        buddy = buddyImage;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_internal_view,parent,false);

        TextView userName = (TextView) rowItem.findViewById(R.id.result_username);
        TextView distance = (TextView) rowItem.findViewById(R.id.result_distance);

        if (ResultActivity.myBuddyList.contains(myBuddy.get(position).getUserName())) {
            ImageView buddyView = (ImageView) rowItem.findViewById(R.id.result_buddy_image);
            buddyView.setImageResource(buddy);
        }

        userName.setText(myBuddy.get(position).getUserName());
        distance.setText(String.valueOf(myBuddy.get(position).getDistance()) + "miles");

        return rowItem;
    }
}
