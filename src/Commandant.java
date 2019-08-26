public class Commandant extends Human{
    private Hostel hostel;
    private int id=0;

    Commandant(Hostel hostel) {
        this.hostel = hostel;
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
        hostel.administration.passList.remove(pass);
    }
    void checkBook() {
        System.out.println("Commandant checks his book.");
        for (Pass pass: hostel.administration.passList) {
            if(pass.note == Notes.MOVING_OUT) {
                removeStudent(pass);
            }
        }
    }
}
