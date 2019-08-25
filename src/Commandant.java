import java.util.List;

public class Commandant extends Human{
    Hostel hostel = Hostel.hostel;
    void addStudent() {
        int currentWeek =hostel.hostelTime.getCurrentWeek();
        System.out.println("Enter Name (First Last)");
        String firstName = "John";
        String lastName = "Connor";
        System.out.println("Enter course");
        int course = 1;
        Student newbie = new Student(firstName, lastName, course, currentWeek);
        for (Floor floor :hostel.floors) {
            if (floor.addStudent(newbie)) {
                System.out.println("Settled");
                return;
            }
        }
        System.out.println("The hostel is full. Sorry.");
    }

    void removeStudents() {

    }
}
