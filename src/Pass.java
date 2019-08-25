import java.sql.DatabaseMetaData;
import java.util.Date;

public class Pass {
    private int id;
    private Date startDate;
    boolean payState;

    Pass(Date startDate, int id) {
        this.startDate = startDate;
        this.id = id;
    }

    int getId() {
        return id;
    }

    Date getStartDate() {
        return startDate;
    }

}
