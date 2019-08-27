import java.util.List;

public class Guard {
    void moveOutStudents(List<Student> movingOutStudents) {
        System.out.print("Guard moves out ");
        for (Student student : movingOutStudents) {
            System.out.print(student.getStringName() + ", ");
            student.pass.room.moveOutRoommate(student);
        }
        System.out.println();
        System.out.println();
    }
}
