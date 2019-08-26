import java.util.stream.Stream;

//For testing - int Stream, at refactoring add LocalDate Stream
class Time {

    private int currentWeek;

    Time() {
        currentWeek=0;
    }

    int getCurrentWeek() {
        return currentWeek;
    }

    int getLivingWeek(int startWeek) {
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
