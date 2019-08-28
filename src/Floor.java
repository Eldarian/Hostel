import java.util.ArrayList;

public class Floor{
    int floorNumber;
    ArrayList<Room> rooms;
    Time hostelTime;

    Floor(int floorNumber, Time hotelTime) {
        this.floorNumber = floorNumber;
        rooms = initRooms();
        this.hostelTime = hotelTime;
    }

    private ArrayList<Room> initRooms() {
        ArrayList<Room> rooms = new ArrayList<>(Hostel.roomsOnFloor);
        for (int i = 0; i<Hostel.roomsOnFloor; i++) {
            rooms.add(new Room(i+1));
        }
        return rooms;
    }

    boolean addStudent(Student newbie) {
        for (Room room : rooms) {
            if (room.addRoommate(newbie)) return true;
        }
        return false;
    }

    void updateCourse() {
        for (Room room: rooms) {
            for (Student student: room.getRoommates()) {
                if (student.course < 4) {
                    student.course++;
                } else {
                    System.out.println(student.getStringName() + " is going to leave the hostel because he is graduate.");
                    student.getPass().note = Notes.MOVING_OUT;
                }
            }
        }
    }

    void update() {
        for (Room room: rooms) {
            room.setCleaner();
            for (Student student: room.getRoommates()) {
                student.update();
            }
        }

    }
}