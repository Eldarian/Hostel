import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hostel {
    static final int floorsInHostel = 4;
    static final int roomsOnFloor = 4;
    static final int studentsInRoom = 3;
    static final int yearLimit = 10;
    static Hostel hostel;

    File file = new File("students.txt");
    private Scanner scanner;

    Time hostelTime;
    List<Floor> floors;
    Administration administration;


    Hostel() {
        floors = initFloors();
        hostelTime = new Time();
        administration = new Administration(this, hostelTime);

        try {
            scanner = new Scanner(file);
            timeLapse();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception!");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }
    }
    private List<Floor> initFloors() {
        ArrayList<Floor> floors = new ArrayList<>(floorsInHostel);
        for (int i = 0; i<floorsInHostel; i++) {
            floors.add(new Floor(i+1, hostelTime));
        }
        System.out.println("Hostel has been initialized.");
        return floors;
    }
    private void timeLapse() throws InterruptedException, FileNotFoundException {
        System.out.println("Welcome to the hell hostel!");
        while(hostelTime.getCurrentYear() < yearLimit) {
            administration.updateWeek();
            generateStudentRequests();
            //Thread.sleep(1000);
        }
        System.out.println("And they lived happily ever after.");
    }

    void generateStudentRequests() throws FileNotFoundException{
        Random r = new Random();
        for (int i = 0; i < r.nextInt(10); i++) {
            studentRequest();
        }
    }
    private void studentRequest() throws FileNotFoundException{
        //scanner.useDelimiter("\\s");
        if (scanner.hasNextLine()) {
            administration.commandant.addStudent(scanner.next(), scanner.next(), scanner.nextInt());
        } else {
            scanner.close();
            scanner = new Scanner(file);
            administration.commandant.addStudent(scanner.next(), scanner.next(), scanner.nextInt());
        }
    }
    public static void main(String...args) {
        hostel = new Hostel();
    }


}
