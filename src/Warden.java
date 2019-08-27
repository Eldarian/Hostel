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

    List<Student> checkFloor() {
        System.out.println("Warden of floor " + floor.floorNumber + " has started checking rooms.");
        List<Student> illegals = new ArrayList<>();
        for(Room room: floor.rooms) {
            if(room.hasRoommates()){
                checkRoom(room, illegals);
            }
        }
        System.out.println(illegals);
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
        System.out.println("Checking is room number " + room.roomNumber + " on the " + floor.floorNumber + " floor clean...");
        room.printRoomMates(floor.floorNumber);
        if(!room.isClean) {
            System.out.println("Dirty!");
            makeWarn(room.getCleaner());
        } else {
            System.out.println("Clean!");
        }
        for (Student student : room.getRoommates()) {
            if (!checkStudent(student)) illegals.add(student);
        }
        System.out.println();
    }

    private boolean checkStudent(Student student) {
        if (!student.pass.payState) {
            student.pass.note = Notes.MOVING_OUT;
            System.out.println(student.getStringName() + " " + student.pass.getStartDate() +  " will be cancelled for debts.");
        }
        return student.pass.isValid;
    }

    private void makeWarn(Student student) {
        if (student.pass.note == Notes.OK) {
            student.pass.note = Notes.WARNED;
            System.out.println(student.getStringName() + " has been warned.");
        }
        else {
            student.pass.note = Notes.MOVING_OUT;
            System.out.println(student.getStringName() + " will be cancelled for bad discipline.");
        }
    }
}
