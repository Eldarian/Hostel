import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class HostelRunnable implements Runnable {
    static final int floorsInHostel = 4;
    static final int roomsOnFloor = 4;
    static final int studentsInRoom = 3;
    static final int yearLimit = 5;
    private static File file = new File("students.txt");
    private static Scanner scanner;


    private Time hostelTime;
    private Hostel hostel;

    private static volatile Queue<Human> entranceQueue = new ArrayBlockingQueue<Human>(5);

    //queue for testing entrance for guard
    private static volatile Queue<Student> studentsWithPass = new ArrayBlockingQueue<Student>(5);

    HostelRunnable() {
        hostelTime = new Time();
        hostel = new Hostel(entranceQueue, studentsWithPass, hostelTime);
    }

    public void run() {
        System.out.println("Welcome to the hell hostel!");
        while(hostelTime.getCurrentYear() < yearLimit) {
            hostel.updateWeek();
            for(int i=0; i < entranceQueue.size(); i++) {
                hostel.getGuard().admit();
            }
            Thread.yield();
        }
        System.out.println("And they lived happily ever after.");
    }



    private static void generateRequests() throws FileNotFoundException{
        Random r = new Random();
        for (int i = 0; i < r.nextInt(2); i++) {
            guestRequest();
        }
        for (int i = 0; i < r.nextInt(2); i++) {
            passStudentRequest();
        }
        for (int i = 0; i < r.nextInt(2); i++) {
            noPassStudentRequest();
        }
    }
    private static void guestRequest() throws FileNotFoundException{
        if (scanner.hasNextLine()) {
            lineUpForEnter(new Guest(scanner.next(), scanner.next(), scanner.nextInt()));

        } else {
            scanner.close();
            scanner = new Scanner(file);
            lineUpForEnter(new Guest(scanner.next(), scanner.next(), scanner.nextInt()));
        }
    }
    private static void noPassStudentRequest() throws FileNotFoundException{
        if (scanner.hasNextLine()) {
            lineUpForEnter(new Student(scanner.next(), scanner.next(), scanner.nextInt(), null));

        } else {
            scanner.close();
            scanner = new Scanner(file);
            lineUpForEnter(new Student(scanner.next(), scanner.next(), scanner.nextInt(), null));
        }
    }
    private static void passStudentRequest(){
        if (studentsWithPass.peek() != null) {
            lineUpForEnter(studentsWithPass.poll());
        }
    }


    private static void lineUpForEnter(Human human) {
        entranceQueue.offer(human);
    }


    public static void main(String...args) throws FileNotFoundException {
        scanner = new Scanner(file);
        generateRequests();
        Thread hostelThread = new Thread(new HostelRunnable());
        hostelThread.start();
        while (hostelThread.isAlive()) {
            generateRequests();
        }
    }


    class Hostel {
        private Building building;
        private Time hostelTime;
        private Commandant commandant;
        private Guard guard;
        private Map<Building.Floor, Warden> wardenMap;
        private ArrayList<Pass> passList;

        Hostel(Queue<Human> entranceList, Queue<Student> studentsWithPass, Time hostelTime) {
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
            for (Building.Floor.Room room : floor.rooms) {
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

    static class Time {

        private int currentWeek;

        Time() {
            currentWeek=0;
        }

        int getCurrentWeek() {
            return currentWeek;
        }

        private int getLivingWeek(int startWeek) {
            return currentWeek-startWeek;
        }

        int getCurrentYear() {
            return currentWeek/52 + 1;
        }

        int getLivingYear(int startWeek) {
            return getLivingWeek(startWeek)/52 + 1;
        }

        void increaseTime() {
            currentWeek++;
        }
    }
}
