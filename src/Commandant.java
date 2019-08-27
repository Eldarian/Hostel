import java.util.ArrayList;

public class Commandant extends Human{
    private Hostel hostel;
    ArrayList<Pass> passList;
    private int id=0;

    Commandant(Hostel hostel, ArrayList<Pass> passList) {
        this.hostel = hostel;
        this.passList = passList;
    }

    void addStudent(String firstName, String lastName, int course) {
        int currentWeek = hostel.hostelTime.getCurrentWeek();
        Student newbie = new Student(firstName, lastName, course, currentWeek, id++);
        System.out.print("Commandant: " + newbie.getStringName() + ", course " + course);
        for (Floor floor : hostel.floors) {
            if (floor.addStudent(newbie)) {
                System.out.println(" has settled");
                hostel.administration.passList.add(newbie.pass);
                return;
            }
        }
        System.out.println(" can't be settled. The hostel is full. Sorry.");
    }
    private void removeStudent(Pass pass) {
        System.out.println("Commandant cancels pass for " + pass.owner);
        pass.isValid = false;
        hostel.administration.passList.remove(pass);
    }
    void checkBook() {
        System.out.println("Commandant checks his book.");
        for (int i = 0; i < passList.size(); i++) {
            if(passList.get(i).note == Notes.MOVING_OUT) {
                removeStudent(passList.get(i));
            }
        }
    }
}
