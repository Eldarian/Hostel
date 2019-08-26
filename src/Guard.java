import java.util.List;

public class Guard {
    void moveOutStudents(List<Student> movingOutStudents) {
        for (Student student : movingOutStudents) {
            student.pass.room.moveOutRoommate(student);
        }
    }
}
