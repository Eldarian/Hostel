import java.util.Random;

public class Student extends Human {
    int course;
    int settleTime;
    boolean hasPaid = false;
    Pass pass;

    Student(String firstName, String lastName, int course, int settleTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.settleTime = settleTime;
    }

    void pay() {
        if(!hasPaid) {
            Random r = new Random();
            hasPaid = r.nextInt(10)<8;
        }
    }

    boolean cleanRoom() {
        Random r = new Random();
        return r.nextInt(10)<5;
    }

    String getStringName() {
        return firstName + " " + lastName;
    }


}
