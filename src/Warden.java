import java.util.ArrayList;
import java.util.List;

class Warden extends Student{
    private Building.Floor floor;

    Warden(Student student, Building.Floor floor) {
        super(student.firstName, student.lastName, student.course, student.getPass());
        this.floor = floor;
    }

    List<Student> checkFloor() {
        System.out.println("Warden of floor " + floor.floorNumber + " has started checking rooms.");
        List<Student> illegals = new ArrayList<>();
        for(Building.Floor.Room room: floor.rooms) {
            if(room.hasRoommates()){
                checkRoom(room, illegals);
            }
        }
        return illegals;
    }

    private void checkRoom(Building.Floor.Room room, List<Student> illegals) {
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
        if (!student.getPass().payState) {
            student.getPass().note = Pass.Notes.MOVING_OUT;
            System.out.println(student.getStringName() + " " + student.getPass().getStartDate() +  " will be cancelled for debts.");
        }
        return student.getPass().isValid;
    }

    private void makeWarn(Student student) {
        if (student.getPass().note == Pass.Notes.OK) {
            student.getPass().note = Pass.Notes.WARNED;
            System.out.println(student.getStringName() + " has been warned.");
        }
        else {
            student.getPass().note = Pass.Notes.MOVING_OUT;
            System.out.println(student.getStringName() + " will be cancelled for bad discipline.");
        }
    }
}
