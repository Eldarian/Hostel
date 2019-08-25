import java.util.List;
import java.util.Map;

public class Warden extends Student{
    Map<Pass, Notes> warningList;

    public Warden(Student student, Map<Pass, Notes> bookOfStudents) {
        super(student.firstName, student.lastName, student.course, student.settleTime);
        this.warningList = bookOfStudents;
    }

    void checkRoom(Room room, int number) {
        System.out.println("checking is room number " + number + " clean...");
        if(!room.isClean) warningList.put(room.getCleaner(), );

    }


    void checkStudent(Student student) {
        if (!student.pass.payState) {
            warningList.replace(student.pass, Notes.HAVE_NOT_PAID);
        }
    }
}
