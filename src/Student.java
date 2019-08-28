import java.util.Random;

public class Student extends Human {
    int course;
    private Pass pass;

    Student(String firstName, String lastName, int course, int settleTime, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.pass = new Pass(this.getStringName(), settleTime, id);
    }

    Student(String firstName, String lastName, int course, Pass pass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
        this.pass = pass;
    }

    Pass getPass() {
        return pass;
    }

    private void pay() {
        Random r = new Random();
        pass.payState = r.nextInt(10)<8;;
    }

    private void cleanRoom() {
        Random r = new Random();
        pass.room.isClean = r.nextInt(10)<8;
    }

    String getStringName() {
        return firstName + " " + lastName + ", " + course;
    }

    void update() {
        if(!pass.payState) {
            pay();
        }
        if(pass.room.getCleaner()==this) {
            cleanRoom();
        }
    }


}
