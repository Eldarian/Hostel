import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hostel {
    static final int floorsInHostel = 4;
    static final int roomsOnFloor = 8;
    static final int studentsInRoom = 3;
    static Hostel hostel;


    Time hostelTime;
    List<Floor> floors;
    Commandant commandant;
    Secure guard;

    Hostel() {
        floors = initFloors();
        hostelTime = new Time();
        commandant = new Commandant();
        guard = new Secure();
        try {
            timeLapse();
        } catch (InterruptedException e) {

        }

    }

    private void timeLapse() throws InterruptedException {
        while(true) {
            if (hostelTime.getCurrentWeek()%4==0) payday();
            if (hostelTime.getCurrentWeek()%52==0) newCourse();
            hostelTime.increaseTime();
            Thread.sleep(1000);
        }
    }

    void payday() {
        System.out.println("It's payday...");
    }

    void newCourse() {
        System.out.println("New course starts.");
        for(Floor floor: floors) {
            floor.updateCourse();
        }
    }

    private List<Floor> initFloors() {
        ArrayList<Floor> floors = new ArrayList<>(floorsInHostel);
        for (int i = 0; i<floorsInHostel; i++) {
            floors.add(new Floor());
        }
        return floors;
    }

    public static void main(String...args) {
        hostel = new Hostel();
    }


}
