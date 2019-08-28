import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hostel implements Runnable{
    static final int floorsInHostel = 4;
    static final int roomsOnFloor = 4;
    static final int studentsInRoom = 3;
    static final int yearLimit = 10;
    private static File file = new File("students.txt");
    private static Scanner scanner;
    private Lock weekLock = new ReentrantLock();


    private Time hostelTime;
    private Administration administration;

    private static volatile Queue<Human> entranceList = new ArrayBlockingQueue<Human>(10);
    private static volatile Queue<Student> studentsWithPass = new ArrayBlockingQueue<Student>(10); //queue for testing entrance for guard


    public Hostel() {
        hostelTime = new Time();
        administration = new Administration(entranceList, studentsWithPass, hostelTime);
    }

    public void run() {
        System.out.println("Welcome to the hell hostel!");
        while(hostelTime.getCurrentYear() < yearLimit) {
            administration.updateWeek();
            administration.getGuard().admit();
            Thread.yield();
        }
        System.out.println("And they lived happily ever after.");
    }



    private static void generateRequests() throws FileNotFoundException{
        Random r = new Random();
        for (int i = 0; i < r.nextInt(5); i++) {
            guestRequest();
        }
        for (int i = 0; i < r.nextInt(5); i++) {
            passStudentRequest();
        }
        for (int i = 0; i < r.nextInt(5); i++) {
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

    private static void passStudentRequest() throws FileNotFoundException{
        if (studentsWithPass.peek() != null) {
            lineUpForEnter(studentsWithPass.poll());
        }
    }

    private static void lineUpForEnter(Human human) {
        entranceList.offer(human);
    }

    public static void main(String...args) throws FileNotFoundException {
        scanner = new Scanner(file);
        generateRequests();
        Thread hostelThread = new Thread(new Hostel());
        hostelThread.start();
        while (hostelThread.isAlive()) {
            generateRequests();
        }
    }


}
