package starace.learn.com.studybuddy;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Custom Cursor Adaptor for the DetailActivity
 */
public class DetailCursorAdapter extends CursorAdapter {
    private static final String TAG_DETAIL_ADAPTER = "DetailCursorAdapter";
    public DetailCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.detail_internal_view,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView subject = (TextView) view.findViewById(R.id.detail_subject);
        TextView level = (TextView) view.findViewById(R.id.detail_level);
        TextView mClass = (TextView) view.findViewById(R.id.detail_class);

        subject.setText("- " + cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_SUBJECT)));

        if (cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_CLASS)) != null) {
            level.setText("- " + cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_LEVEL)));
            mClass.setText("- " +cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_CLASS)));

        } else if (cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_LEVEL)) != null) {
            level.setText(cursor.getString(cursor.getColumnIndex(BuddySQLHelper.INTEREST_COLUMN_LEVEL)));

        }

    }
}
