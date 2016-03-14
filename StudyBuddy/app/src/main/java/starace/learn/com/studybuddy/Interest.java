package starace.learn.com.studybuddy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mstarace on 3/10/16.
 */
public class Interest implements Parcelable {

    private String subject;
    private String level;
    private String myClass;

    public Interest(String subject, String level, String myClass) {
        this.level = level.toLowerCase();
        this.myClass = myClass.toLowerCase();
        this.subject = subject.toLowerCase();
    }

    public Interest(String subject, String level) {
        this.level = level.toLowerCase();
        this.subject = subject.toLowerCase();
    }

    public Interest(String subject) {
        this.subject = subject.toLowerCase();
    }

    public Interest() {
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level.toLowerCase();
    }

    public String getMyClass() {
        return myClass;
    }

    public void setMyClass(String myClass) {
        this.myClass = myClass.toLowerCase();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject.toLowerCase();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(subject);
        out.writeString(level);
        out.writeString(myClass);
    }

    private Interest(Parcel in) {
        subject = in.readString();
        level = in.readString();
        myClass = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    public static final Parcelable.Creator<Interest> CREATOR =
            new Parcelable.Creator<Interest>() {
                public Interest createFromParcel(Parcel in) {
                    return new Interest(in);
                }

                public Interest[] newArray(int size) {
                    return new Interest[size];
                }
            };


}
