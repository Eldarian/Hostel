import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class Guard extends Human{
    private volatile Queue<Human> entranceList;
    private Administration administration;

    Guard(Queue<Human> entranceList, Administration administration) {
        this.entranceList = entranceList;
        this.administration = administration;
    }

    void admit() {
        if (entranceList != null && entranceList.peek() instanceof Guest) {
            administration.getCommandant().addStudent((Guest) Objects.requireNonNull(entranceList.poll()));
        }
        if (entranceList.peek() != null && entranceList.peek() instanceof Student) {
            Student student = (Student) Objects.requireNonNull(entranceList.poll());
            if (administration.isInPassList(student.getPass())) {
                System.out.println("Guard admitted " + student.getStringName() + " with pass.");
            } else {
                System.out.println("Guard didn't let " + student.getStringName() + " without pass.");
            }
        }
    }

    void moveOutStudents(List<Student> movingOutStudents) {
        System.out.print("Guard moves out ");
        for (Student student : movingOutStudents) {
            System.out.print(student.getStringName() + ", ");
            student.getPass().room.moveOutRoommate(student);
        }
        System.out.println();
        System.out.println();
    }
}
