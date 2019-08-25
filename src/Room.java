import java.util.ArrayList;
import java.util.Iterator;

class Room{
    Floor floor;
    boolean isClean;
    private Student cleaner;
    private ArrayList<Student> roommates = new ArrayList<>();

    boolean addRoommate(Student newbie) {
        if (roommates.size() < Hostel.studentsInRoom) {
            roommates.add(newbie);
            if (roommates.size()==1) {
                iter = roommates.iterator();
                setCleaner();
            }
            return true;
        }
        return false;
    }

    boolean moveOutRoommate(Student loser) {
        if (!roommates.contains(loser)) return false;
        if (loser == cleaner) setCleaner();
        if (loser instanceof Warden)
        roommates.remove(loser);
        return true;
    }

    private Iterator<Student> iter;
    Student getCleaner() {
        return cleaner;
    }

    ArrayList<Student> getRoommates() {
        return roommates;
    }
    private void setCleaner() {
        if (roommates.size()>0) {
            cleaner = iter.hasNext() ? iter.next() : roommates.get(0);
        }
    }
}
