package starace.learn.com.studybuddy;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/23/16.
 */
public class CheckInterest implements Parcelable {
    private static final String TAG_CHECK = "CheckInterest";
    private ArrayList<String> interestArrayList;
    private String subject;
    private String level;
    private String myClass;
    private String message;
    private int size;

    public CheckInterest(){}

//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public String getMyClass() {
//        return myClass;
//    }
//
//    public void setMyClass(String myClass) {
//        this.myClass = myClass;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public int getSize() {
//        return size;
//    }
//
//    public void setSize(int size) {
//        this.size = size;
//    }

    public static CheckInterest checkInput(AutoCompleteTextView subject, AutoCompleteTextView level, EditText myClass,
                                           ArrayList<String> subArray, ArrayList<String> levelArray) {

        CheckInterest check = new CheckInterest();
        check = checkSubject(subject, subArray, check);

        check = checkLevel(level, levelArray, check);

        check = checkClass(level,myClass,check);
        check = checkSize(check);
        
        return check;
    }

    private static CheckInterest checkSubject(AutoCompleteTextView subject, ArrayList<String> subjectArray, CheckInterest check){

        if (subjectArray.contains(subject.getText().toString())){
            check.setSubject(subject.getText().toString());
        } else if(!subjectArray.contains(subject.getText().toString())){
            check.setMessage("Please choose a \"Subject\" from the list");
        }

        return check;
    }

    private static CheckInterest checkLevel(AutoCompleteTextView level,ArrayList<String> levelArray, CheckInterest check) {

        if (levelArray.contains(level.getText().toString())) {
            check.setLevel(level.getText().toString());
        } else if (!level.getText().toString().equals(""))
            if (check.getMessage() == null) {
                check.setMessage("Please choose a \"Level\" from the list.");
            }
        return check;
    }

    private static CheckInterest checkClass(EditText level, EditText myClass, CheckInterest check) {

        if (!level.getText().toString().equals("") && !myClass.getText().toString().equals("")) {
            check.setMyClass(myClass.getText().toString());
        } else if (level.getText().toString().equals("") && !myClass.getText().toString().equals("")) {
            if (check.getMessage() == null) {
                check.setMessage("Please include a \"Level\" from the list");
            }
        }

        return check;
    }

    private static CheckInterest checkSize(CheckInterest check){

        if (check.getMyClass() != null){
            check.setSize(3);
        } else if (check.getLevel() != null){
            check.setSize(2);
        } else {
            check.setSize(1);
        }

        return check;
    }

    public void writeToParcel(Parcel out, int flags) {
        //out.writeString(subject);
        //out.writeString(level);
        //out.writeString(myClass);
        out.writeString(message);
        //out.writeInt(size);
        out.writeStringList(interestArrayList);
    }

    private CheckInterest(Parcel in) {
        //subject = in.readString();
        //level = in.readString();
        //myClass = in.readString();
        message = in.readString();
        //size = in.readInt();
        interestArrayList = in.readArrayList(null);
    }

    public int describeContents() {
        return this.hashCode();
    }

    public static final Parcelable.Creator<CheckInterest> CREATOR =
            new Parcelable.Creator<CheckInterest>() {
                public CheckInterest createFromParcel(Parcel in) {
                    return new CheckInterest(in);
                }

                public CheckInterest[] newArray(int size) {
                    return new CheckInterest[size];
                }
            };

}
