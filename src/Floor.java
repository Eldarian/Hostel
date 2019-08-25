import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Floor{
    ArrayList<Room> rooms;
    Warden warden;
    Map<Pass, Notes> bookOfStudents;
    Floor() {
        rooms = initRooms();
    }

    private ArrayList<Room> initRooms() {
        ArrayList<Room> rooms = new ArrayList<>(Hostel.roomsOnFloor);
        for (int i = 0; i<Hostel.roomsOnFloor; i++) {
            rooms.add(new Room());
        }
        return rooms;
    }

    boolean addStudent(Student newbie) {
        for (Room room: rooms) {
            if (room.addRoommate(newbie)) return true;
        }
        return false;
    }

    boolean moveOutStudent(Student movingOutStud) {
        for (Room room: rooms) {
            if (room.moveOutRoommate(movingOutStud)) {
                if (movingOutStud instanceof Warden) {
                    appointWarden();
                }
                return true;
            }
        }
        return false;
    }

    private void appointWarden() {
        for (Room room : rooms) {
            for (int i = 0; i < room.getRoommates().size(); i++) {
                Student candidate = room.getRoommates().get(i);
                if(Hostel.hostel.hostelTime.getLivingYear(candidate.settleTime) > 1) {
                    warden = new Warden(candidate, bookOfStudents);
                    room.getRoommates().remove(i);
                    room.addRoommate(warden);
                    System.out.println(warden.getStringName() + " is Warden.");
                    return;
                }
            }
        }
    }

    void updateCourse() {
        for (Room room: rooms) {
            for (Student student: room.getRoommates()) {
                if (student.course < 4) {
                    student.course++;
                }
            }
        }
    }
}
