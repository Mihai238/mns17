package at.tuwien.mns17.androidgeolocation.location_report;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocationReport implements Serializable {

    private static Long currentId = 1L;

    private Long id = currentId++;
    private Long timestamp = Calendar.getInstance().getTimeInMillis();

    public String getName() {
        return "Location report #" + id;
    }

    public String getTime() {
        return SimpleDateFormat.getInstance().format(new Date(timestamp));
    }

    @Override
    public String toString() {
        return getName() + ", at " + getTime();
    }
}
