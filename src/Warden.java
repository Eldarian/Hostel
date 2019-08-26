import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Warden extends Student{
    Floor floor;

    public Warden(Student student, Floor floor) {
        super(student.firstName, student.lastName, student.course, student.pass);
        this.floor = floor;
    }

    LinkedList<Student> checkFloor() {
        System.out.println("Warden of floor " + floor.floorNumber + " has started checking rooms.");
        LinkedList<Student> illegals = new LinkedList<>();
        for(Room room: floor.rooms) {
            checkRoom(room, illegals);
        }
        return illegals;
    }

    /*void checkRooms() {
        if(warden!=null) {
            System.out.println("Warden of floor " + floorNumber + " has started checking rooms.");
            for(Room room : rooms) {
                warden.checkRoom(room, rooms.indexOf(room));
            }
        } else {
            System.out.println("The floor "+ floorNumber + " is empty.");
        }
    }*/

    void checkRoom(Room room, List<Student> illegals) {
        System.out.println("checking is room number " + room.roomNumber + " clean...");
        if(!room.isClean) {
            makeWarn(room.getCleaner());
        }
        for (Student student : room.getRoommates()) {
            if (!checkStudent(student)) illegals.add(student);
        }

    }

    private boolean checkStudent(Student student) {
        if (!student.pass.payState) {
            student.pass.note = Notes.MOVING_OUT;
        }
        return !student.pass.isValid;
    }

    private void makeWarn(Student student) {
        if (student.pass.note ==Notes.OK) student.pass.note = Notes.WARNED;
        else student.pass.note = Notes.MOVING_OUT;
    }
}
