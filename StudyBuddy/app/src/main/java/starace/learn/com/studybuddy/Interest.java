package starace.learn.com.studybuddy;

/**
 * Created by mstarace on 3/10/16.
 */
public class Interest {

    private String subject;
    private String level;
    private String myClass;

    public Interest(String level, String myClass, String subject) {
        this.level = level;
        this.myClass = myClass;
        this.subject = subject;
    }

    public Interest() {
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMyClass() {
        return myClass;
    }

    public void setMyClass(String myClass) {
        this.myClass = myClass;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
