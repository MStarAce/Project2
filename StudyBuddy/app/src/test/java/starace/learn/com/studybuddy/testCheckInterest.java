package starace.learn.com.studybuddy;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class testCheckInterest {


    /**
     * test subject set and get with a null ArrayList in the object
     */
    @Test
    public void testSubject(){
        CheckInterest subjectInterest = new CheckInterest();

        String expected = "Totally Sweet";
        subjectInterest.setSubject(expected);
        String actual = subjectInterest.getSubject();
        assertEquals(expected, actual);
    }

    /**
     * test subject set and get with a not null ArrayList in the object
     */
    @Test
    public void testSubjectNotNull () {
        CheckInterest subjectInterest = new CheckInterest();
        String expected = "Totally Sweet";
        subjectInterest.setSubject(expected);

        String expectedNotNull = "Radical";
        subjectInterest.setSubject(expectedNotNull);
        String actualNotNull = subjectInterest.getSubject();
        assertEquals(expectedNotNull, actualNotNull);
    }

    /**
     * test level get and set with a null ArrayList
     */
    @Test
    public void testLevelNull(){
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setLevel(input);

        String actualNull = subjectInterest.getLevel();
        assertEquals(null,actualNull);
    }

    /**
     * test level get and set with a null level index
     */
    @Test
    public void testLevelNullIndex(){
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setSubject(input);

        String actualNullIndex = subjectInterest.getLevel();
        assertEquals(null,actualNullIndex);
    }

    /**
     * test level with a not null level index
     */
    @Test
    public void testLevel(){
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setSubject(input);
        subjectInterest.setLevel(input);

        String actual = subjectInterest.getLevel();
        assertEquals(input,actual);
    }

    /**
     * test level get and set with a null ArrayList
     */
    @Test
    public void testMyClassNull(){
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setMyClass(input);

        String actualNull = subjectInterest.getMyClass();
        assertEquals(null,actualNull);
    }

    /**
     * test myClass get and set with a null myClass index
     */
    @Test
    public void testMyClassNullIndex(){
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setSubject(input);
        subjectInterest.setLevel(input);

        String actualNullIndex = subjectInterest.getMyClass();
        assertEquals(null,actualNullIndex);
    }

    /**
     * test myClass with a not null myClass index
     */
    @Test
    public void testMyClass(){
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setSubject(input);
        subjectInterest.setLevel(input);
        subjectInterest.setMyClass(input);

        String actual = subjectInterest.getMyClass();
        assertEquals(input,actual);
    }

    /**
     * test set and get message
     */
    @Test
    public void testMessage() {
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setMessage(input);

        String actual = subjectInterest.getMessage();
        assertEquals(input,actual);
    }

    /**
     * tests get interestArrayList
     * interestArrayList is only set through the individual index set methods
     */
    @Test
    public void testArrayListGet(){
        CheckInterest subjectInterest = new CheckInterest();
        String input = "Super Cool";
        subjectInterest.setSubject(input);
        subjectInterest.setLevel(input);
        subjectInterest.setMyClass(input);

        ArrayList<String> actualArrayList = subjectInterest.getInterestArrayList();
        int actualSize = actualArrayList.size();
        assertEquals(3,actualSize);
    }

}
