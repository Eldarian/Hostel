import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

class Room {
    int roomNumber;
    boolean isClean;
    private Student cleaner;
    private ArrayList<Student> roommates = new ArrayList<>();

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    boolean addRoommate(Student newbie) {
        if (roommates.size() < Hostel.studentsInRoom) {
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
