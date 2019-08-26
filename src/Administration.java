import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Administration {
    Hostel hostel;
    Time hostelTime;
    Commandant commandant;
    Guard guard;
    Map<Floor, Warden> wardenMap;
    ArrayList<Pass> passList;

    public Administration(Commandant commandant, Hostel hostel, Time hostelTime) {
        passList = new ArrayList<>();
        this.commandant = commandant;
        this.hostel = hostel;
        this.hostelTime = hostelTime;
        this.wardenMap = initWardens();
    }

    Map<Floor, Warden> initWardens() {
        Map<Floor, Warden> wardenMap = new HashMap<>();
        for(Floor floor: hostel.floors) {
            wardenMap.put(floor, null);
        }
        return wardenMap;
    }

    public Commandant getCommandant() {
        return commandant;
    }

    public Guard getGuard() {
        return guard;
    }

    void updateWeek() {
        hostelTime.increaseTime();
        System.out.println("Year " + hostelTime.getCurrentYear() + ", week " + hostelTime.getCurrentWeek()%52);
        if (hostelTime.getCurrentWeek()%52==0) newCourse();
        if (hostelTime.getCurrentWeek()%4==1) payday();
        checkHostel();
        commandant.checkBook();
    }

    boolean checkWardenOnTheFloor(Floor floor) {
        return wardenMap.get(floor) != null && wardenMap.get(floor).pass.note!=Notes.MOVING_OUT;
    }

    void newCourse() {
        System.out.println("New course starts.");
        for(Floor floor: hostel.floors) {
            floor.updateCourse();
        }
    }
    void payday() {
        System.out.println("It's payday...");
        for(Pass pass: passList) {
            pass.payState = false;
        }
    }

    Warden appointWarden(Floor floor) {
        for (Room room : floor.rooms) {
            for (int i = 0; i < room.getRoommates().size(); i++) {
                Student candidate = room.getRoommates().get(i);
                if (hostelTime.getCurrentYear() == 1 || hostelTime.getLivingYear(candidate.pass.getStartDate()) > 1) {
                    Warden warden = new Warden(candidate, floor);
                    room.getRoommates().remove(i);
                    room.addRoommate(warden);
                    System.out.println(warden.getStringName() + " is Warden.");
                    return warden;
                }
            }
        }
        return null;
    }

    void checkHostel() {
        for(Map.Entry<Floor, Warden> pair: wardenMap.entrySet()) {
            if (checkWardenOnTheFloor(pair.getKey()) || appointWarden(pair.getKey())!=null) {
                if(pair.getValue() != null) {
                    LinkedList<Student> movingOut = pair.getValue().checkFloor();
                    if (movingOut != null) guard.moveOutStudents(movingOut);
                }
            }
        }
    }
}
