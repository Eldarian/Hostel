import java.util.ArrayList;
import java.util.List;

public class Building {
    HostelRunnable.Time hostelTime;
    List<Floor> floors;

    Building(HostelRunnable.Time hostelTime) {
        floors = initFloors();
        this.hostelTime = hostelTime;
    }

    private List<Floor> initFloors() {
        ArrayList<Floor> floors = new ArrayList<>(HostelRunnable.floorsInHostel);
        for (int i = 0; i< HostelRunnable.floorsInHostel; i++) {
            floors.add(new Floor(i+1, hostelTime));
        }
        System.out.println("HostelRunnable.Hostel has been initialized.");
        return floors;
    }

    class Floor{
        int floorNumber;
        ArrayList<Room> rooms;
        HostelRunnable.Time hostelTime;

        Floor(int floorNumber, HostelRunnable.Time hotelTime) {
            this.floorNumber = floorNumber;
            rooms = initRooms();
            this.hostelTime = hotelTime;
        }

        private ArrayList<Room> initRooms() {
            ArrayList<Room> rooms = new ArrayList<>(HostelRunnable.roomsOnFloor);
            for (int i = 0; i< HostelRunnable.roomsOnFloor; i++) {
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
                        student.getPass().note = Pass.Notes.MOVING_OUT;
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

        class Room {
            int roomNumber;
            boolean isClean;
            private Student cleaner;
            private ArrayList<Student> roommates = new ArrayList<>();

            Room(int roomNumber) {
                this.roomNumber = roomNumber;
            }

            boolean addRoommate(Student newbie) {
                if (roommates.size() < HostelRunnable.studentsInRoom) {
                    roommates.add(newbie);
                    newbie.getPass().room = this;
                    if (roommates.size()==1) {
                        setCleaner();
                    }
                    return true;
                }
                return false;
            }

            boolean hasRoommates() {
                return roommates.size()!=0;
            }

            void moveOutRoommate(Student loser) {
                if (!roommates.contains(loser)) return;
                if (cleaner == loser) setCleaner();
                roommates.remove(loser);
            }

            Student getCleaner() {
                return cleaner;
            }

            ArrayList<Student> getRoommates() {
                return roommates;
            }

            void setCleaner() {
                if (roommates.size()>0) {
                    for (int i = 0; i<roommates.size(); i++) {
                        if (cleaner==null) {
                            cleaner = roommates.get(0);
                            return;
                        }
                        if (cleaner == roommates.get(i)) {
                                cleaner = i+1 == roommates.size() ? roommates.get(0) : roommates.get(i+1);
                                return;
                        }
                    }
                }
            }

            void printRoomMates(int floorNumber) {
                if (roommates.size()==0) return;
                System.out.println(floorNumber + " " + roomNumber + ":");
                for(Student student: roommates) {
                    if (cleaner == student) System.out.print("(cleaner)");
                    System.out.print(student.getStringName() + ", ");
                }
                System.out.println();
            }
        }
    }
}

