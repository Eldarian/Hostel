import java.util.*;

public class Administration {
    private Building building;
    private Time hostelTime;
    private Commandant commandant;
    private Guard guard;
    private Map<Building.Floor, Warden> wardenMap;
    private ArrayList<Pass> passList;

    Administration(Queue<Human> entranceList, Queue<Student> studentsWithPass, Time hostelTime) {
        passList = new ArrayList<>();
        this.building = new Building(hostelTime);
        this.commandant = new Commandant(building, passList, studentsWithPass);
        this.hostelTime = hostelTime;
        this.wardenMap = initWardens();
        this.guard = new Guard(entranceList, this);
    }

    private Map<Building.Floor, Warden> initWardens() {
        Map<Building.Floor, Warden> wardenMap = new HashMap<>();
        for(Building.Floor floor: building.floors) {
            wardenMap.put(floor, null);
        }
        return wardenMap;
    }

    Commandant getCommandant() {
        return commandant;
    }

    Guard getGuard() {
        return guard;
    }

    boolean isInPassList(Pass pass) {
         return passList.contains(pass);
    }

    void updateWeek() {
        hostelTime.increaseTime();
        System.out.println();
        System.out.println("*****************************************************************************************");
        System.out.println("Year " + hostelTime.getCurrentYear() + ", week " + hostelTime.getCurrentWeek()%52);
        if (hostelTime.getCurrentWeek()%52==0) newCourse();
        if (hostelTime.getCurrentWeek()%4==0) payday();
        for (Building.Floor floor: building.floors) {
            floor.update();
        }
        System.out.println();
        checkHostel();
        System.out.println();
        commandant.checkBook();
    }

    private boolean checkWardenOnTheFloor(Building.Floor floor) {
        return wardenMap.get(floor) != null;
    }

    private void newCourse() {
        System.out.println();
        System.out.println("*****************************************************************************************");
        System.out.println("*****************************************************************************************");
        System.out.println("*****************************************************************************************");
        System.out.println();

        System.out.println("New course starts.");
        for(Building.Floor floor: building.floors) {
            floor.updateCourse();
        }
    }
    private void payday() {
        System.out.println("It's payday...");
        for(Pass pass: passList) {
            pass.payState = false;
        }
    }

    private Warden appointWarden(Building.Floor floor) {
        for (Room room : floor.rooms) {
            for (int i = 0; i < room.getRoommates().size(); i++) {
                Student candidate = room.getRoommates().get(i);
                if (hostelTime.getCurrentYear() == 1 || hostelTime.getLivingYear(candidate.getPass().getStartDate()) > 1) {
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

    private void checkHostel() {
        for(Map.Entry<Building.Floor, Warden> pair: wardenMap.entrySet()) {
            if (checkWardenOnTheFloor(pair.getKey()) || appointWarden(pair.getKey())!=null) {
                if(pair.getValue() != null) {
                    List<Student> movingOut = pair.getValue().checkFloor();
                    if (movingOut.size() != 0) guard.moveOutStudents(movingOut);
                }
            }
        }
    }
}
