package at.tuwien.mns17.androidgeolocation.location_report;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocationReport implements Serializable {
    private Long timestamp = Calendar.getInstance().getTimeInMillis();

    @Override
    public String toString() {
        return "LocationReport " + SimpleDateFormat.getInstance().format(new Date(timestamp));
    }
}
