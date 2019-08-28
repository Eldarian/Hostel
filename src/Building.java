import java.util.ArrayList;
import java.util.List;

public class Building {
    Time hostelTime;
    List<Floor> floors;

    Building(Time hostelTime) {
        floors = initFloors();
        this.hostelTime = hostelTime;
    }

    private List<Floor> initFloors() {
        ArrayList<Floor> floors = new ArrayList<>(Hostel.floorsInHostel);
        for (int i = 0; i< Hostel.floorsInHostel; i++) {
            floors.add(new Floor(i+1, hostelTime));
        }
        System.out.println("Hostel has been initialized.");
        return floors;
    }


}

