package starace.learn.com.studybuddy;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * CheckInterest holds the information to carry out a search
 * has methods to check the validity of inputs from the user
 */
public class CheckInterest implements Parcelable {
    private static final String TAG_CHECK = "CheckInterest";
    private ArrayList<String> interestArrayList;
    private String message;
    public CheckInterest(){}

    //<editor-fold desc="Getters and Setters">
    /**
     * gets the level from the interestArrayList at index 1.
     *
     * returns null if interestArrayList is null or index 1 is null.
     * @return
     */
    public String getLevel() {
        if (interestArrayList != null) {
            if (interestArrayList.size() > 1) {
                return interestArrayList.get(1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * sets the level at index 1 of the interestArrayList.
     *
     * will not execute unless subject has already been added to interestArrayList
     * and interestArrayList is not null
     * @param level
     */
    public void setLevel(String level) {

        if (interestArrayList != null) {
            if (interestArrayList.size() == 1) {
                interestArrayList.add(level);
            } else if (interestArrayList.size() > 1){
                interestArrayList.set(1, level);
            }
        }
    }

    /**
     * gets the myClass object from interestArray at index 2
     *
     * will return null if interestArrayList is null or index 2 is null
     * @return
     */
    public String getMyClass() {
        if (interestArrayList != null) {
            if (interestArrayList.size() > 2) {
                return interestArrayList.get(2);
            } else {
                return null;
            }
        } else{
            return null;
        }
    }

    /**
     * sets the myClass object from interestArrayList at index 2
     *
     * will not execute if interestArrayList is null or index 2
     * @param myClass
     */
    public void setMyClass(String myClass) {
        if (interestArrayList != null) {
            if (interestArrayList.size() == 2) {
                interestArrayList.add(myClass);
            } else if (interestArrayList.size() > 2){
                interestArrayList.set(2, myClass);
            }
        }
    }

    /**
     * gets the subject object from interestArrayList at index 0
     *
     * returns null if the interestArrayList is null.
     * @return
     */
    public String getSubject() {
        if (interestArrayList != null) {
            return interestArrayList.get(0);
        } else {
            return null;
        }
    }

    /**
     * set the subject object from the interestArrayList at index 0
     *
     * will initialize the array if null before adding the subject
     * @param subject
     */
    public void setSubject(String subject) {
        if(interestArrayList == null) {
            interestArrayList = new ArrayList<>();
            interestArrayList.add(subject);
        } else {
            interestArrayList.set(0,subject);
        }

    }

    /**
     * gets the message object from the CheckInterest class
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the message object from the CheckInterest class
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * gets the interestArrayList from the CheckInterest class
     * @return
     */
    public ArrayList<String> getInterestArrayList() {
        return interestArrayList;
    }
    //</editor-fold>

    //<editor-fold desc="Input Check Methods">
    /**
     * calls the checkSubject, checkLevel, and checkClass methods and returns a CheckInterest object
     *
     * @param subject
     * @param level
     * @param myClass
     * @param subArray
     * @param levelArray
     * @return
     */
    public static CheckInterest checkInput(AutoCompleteTextView subject, AutoCompleteTextView level, EditText myClass,
                                           ArrayList<String> subArray, ArrayList<String> levelArray) {

        CheckInterest check = new CheckInterest();
        check = checkSubject(subject, subArray, check);
        check = checkLevel(level, levelArray, check);
        check = checkClass(level, myClass, check);
        return check;
    }

    /**
     * checks the subject input from the user to ensure validity
     *
     * sets a message if it is invalid
     * @param subject
     * @param subjectArray
     * @param check
     * @return
     */
    private static CheckInterest checkSubject(AutoCompleteTextView subject, ArrayList<String> subjectArray, CheckInterest check){

        if (subjectArray.contains(subject.getText().toString())){
            check.setSubject(subject.getText().toString());
        } else if(!subjectArray.contains(subject.getText().toString())){
            check.setMessage("Please choose a \"Subject\" from the list");
        }
        return check;
    }

    /**
     * checks the level input from user to ensure validity
     *
     * sets a message if it is invalid and a message hasn't already been set
     * @param level
     * @param levelArray
     * @param check
     * @return
     */
    private static CheckInterest checkLevel(AutoCompleteTextView level,ArrayList<String> levelArray, CheckInterest check) {

        if (levelArray.contains(level.getText().toString())) {
            check.setLevel(level.getText().toString());
        } else if (!level.getText().toString().equals(""))
            if (check.getMessage() == null) {
                check.setMessage("Please choose a \"Level\" from the list.");
            }
        return check;
    }

    /**
     * checks the class input from the user to make sure it isn't missing a level input
     *
     * sets a message if it is invalid and a message hasn't already been set
     * @param level
     * @param myClass
     * @param check
     * @return
     */
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
    //</editor-fold>

    /**
     * These are the methods to make the object parcelable
     * @param out
     * @param flags
     */
    //<editor-fold desc="Making Object Parcelable">
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(message);
        out.writeSerializable(interestArrayList);
    }

    private CheckInterest(Parcel in) {
        message = in.readString();
        interestArrayList = (ArrayList<String>) in.readSerializable();
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
    //</editor-fold>

}
