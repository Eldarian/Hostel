import java.util.*;

public class Administration {
    Hostel hostel;
    Time hostelTime;
    Commandant commandant;
    Guard guard;
    Map<Floor, Warden> wardenMap;
    ArrayList<Pass> passList;

    public Administration(Hostel hostel, Time hostelTime) {
        passList = new ArrayList<>();
        this.hostel = hostel;
        this.commandant = new Commandant(hostel, passList);
        this.hostelTime = hostelTime;
        this.wardenMap = initWardens();
        this.guard = new Guard();
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
        System.out.println();
        System.out.println("*****************************************************************************************");
        System.out.println("Year " + hostelTime.getCurrentYear() + ", week " + hostelTime.getCurrentWeek()%52);
        if (hostelTime.getCurrentWeek()%52==0) newCourse();
        if (hostelTime.getCurrentWeek()%4==0) payday();
        for (Floor floor: hostel.floors) {
            floor.update();
        }
        System.out.println();
        checkHostel();
        System.out.println();
        commandant.checkBook();
    }

    boolean checkWardenOnTheFloor(Floor floor) {
        return wardenMap.get(floor) != null;
    }

    void newCourse() {
        System.out.println();
        System.out.println("*****************************************************************************************");
        System.out.println("*****************************************************************************************");
        System.out.println("*****************************************************************************************");
        System.out.println();

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
                    wardenMap.put(floor, warden);
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
                    List<Student> movingOut = pair.getValue().checkFloor();
                    if (movingOut.size() != 0) guard.moveOutStudents(movingOut);
                }
            }
        }
    }
}
