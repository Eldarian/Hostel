import java.util.ArrayList;
import java.util.Queue;

class Commandant extends Human{
    private Building building;
    private ArrayList<Pass> passList;
    private int id=0;
    Queue<Student> studentsWithPass;

    Commandant(Building building, ArrayList<Pass> passList, Queue<Student> studentsWithPass) {
        firstName = "El";
        lastName = "Commandante";
        this.building = building;
        this.passList = passList;
        this.studentsWithPass = studentsWithPass;
    }

    void addStudent(Guest guest) {
        int currentWeek = building.hostelTime.getCurrentWeek();
        Student newbie = new Student(guest.firstName, guest.lastName, guest.course, currentWeek, id++);
        System.out.print("Commandant: " + newbie.getStringName() + ", course " + newbie.course);
        for (Building.Floor floor : building.floors) {
            if (floor.addStudent(newbie)) {
                System.out.println(" has settled");
                passList.add(newbie.getPass());
                studentsWithPass.offer(newbie);
                return;
            }
        }
        System.out.println(" can't be settled. The hostel is full. Sorry.");
    }
    private void removeStudent(Pass pass) {
        System.out.println("Commandant cancels pass for " + pass.owner);
        pass.isValid = false;
        passList.remove(pass);
    }
    void checkBook() {
        System.out.println("Commandant checks his book.");
        for (int i = 0; i < passList.size(); i++) {
            if(passList.get(i).note == Pass.Notes.MOVING_OUT) {
                removeStudent(passList.get(i));
            }
        }
    }
}
