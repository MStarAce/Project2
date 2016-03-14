package starace.learn.com.studybuddy;

import java.util.ArrayList;

/**
 * Created by mstarace on 3/10/16.
 */
public class Buddy {

    private String userName;
    private String city;
    private String state;
    private String firstName;
    private String lastName;
    private int distance;
    private ArrayList<String> buddyListArray;
    private ArrayList<Interest> interestArray;


    public Buddy(ArrayList<String> buddyListArray, String city, int distance, String firstName, ArrayList<Interest> interestArray, String lastName, String state, String userName) {
        this.buddyListArray = buddyListArray;
        this.city = city;
        this.distance = distance;
        this.firstName = firstName;
        this.interestArray = interestArray;
        this.lastName = lastName;
        this.state = state;
        this.userName = userName;
    }

    public Buddy(String userName, String firstName, String lastName, String city, String state, int distance) {
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.state = state;
        this.userName = userName;
        this.distance = distance;
        this.buddyListArray = new ArrayList<>();
        this.interestArray = new ArrayList<>();
    }

    public ArrayList<String> getBuddyListArray() {
        return buddyListArray;
    }

    public void setBuddyListArray(ArrayList<String> buddyListArray) {
        this.buddyListArray = buddyListArray;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Interest> getInterestArray() {
        return interestArray;
    }

    public void setInterestArray(ArrayList<Interest> interestArray) {
        this.interestArray = interestArray;
    }

}
