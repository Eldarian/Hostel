import java.sql.DatabaseMetaData;
import java.util.Date;

class Pass {
    private final int id;
    final String owner;
    private final int startDate;
    boolean isValid = true;;
    boolean payState;
    Notes note;
    Room room;

    Pass(String owner, int startDate, int id) {
        this.startDate = startDate;
        this.id = id;
        this.owner = owner;
        this.payState = true;
        this.note = Notes.OK;
    }

    int getId() {
        return id;
    }


    int getStartDate() {
        return startDate;
    }


}
