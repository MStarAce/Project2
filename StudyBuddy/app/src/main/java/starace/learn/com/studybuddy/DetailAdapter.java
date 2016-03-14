package starace.learn.com.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mstarace on 3/10/16.
 */
public class DetailAdapter extends ArrayAdapter {
    List<Interest> myInterest = new ArrayList<>();

    public DetailAdapter(Context context, ArrayList<Interest> objects) {
        super(context, -1, objects);
        myInterest = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_internal_view,parent,false);

        TextView subject = (TextView) rowItem.findViewById(R.id.detail_subject);
        TextView level = (TextView) rowItem.findViewById(R.id.detail_level);
        TextView myClass = (TextView) rowItem.findViewById(R.id.detail_class);

        subject.setText("- Subject: " + myInterest.get(position).getSubject());

        if (myInterest.get(position).getLevel()!= null) {
            level.setText("- Level: " + myInterest.get(position).getLevel());
        }

        if (myInterest.get(position).getLevel()!= null) {
            myClass.setText("- Class: " + myInterest.get(position).getMyClass());
        }
        return rowItem;
    }
}
