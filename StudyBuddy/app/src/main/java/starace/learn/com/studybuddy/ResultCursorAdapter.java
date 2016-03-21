package starace.learn.com.studybuddy;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mstarace on 3/16/16.
 */
public class ResultCursorAdapter extends CursorAdapter {

    public ResultCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.result_internal_view,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView user = (TextView) view.findViewById(R.id.result_username);
        TextView distance = (TextView) view.findViewById(R.id.result_distance);
        ImageView buddy = (ImageView) view.findViewById(R.id.result_buddy_image);
        ImageView userImage = (ImageView) view.findViewById(R.id.result_image);


        user.setText(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.BUDDY_COLUMN_USERNAME)));
        distance.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(BuddySQLHelper.BUDDY_COLUMN_DISTANCE))) + " miles");
        buddy.setImageResource(cursor.getInt(cursor.getColumnIndex(BuddySQLHelper.BUDDY_COLUMN_ISBUDDY)));
        userImage.setImageResource(cursor.getInt(cursor.getColumnIndex(BuddySQLHelper.BUDDY_COLUMN_IMAGE)));

    }
}
