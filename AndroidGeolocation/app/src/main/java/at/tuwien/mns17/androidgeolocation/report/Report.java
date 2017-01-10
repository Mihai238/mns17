package at.tuwien.mns17.androidgeolocation.report;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Report implements Serializable {
    private Long timestamp = Calendar.getInstance().getTimeInMillis();

    @Override
    public String toString() {
        return "Report " + SimpleDateFormat.getInstance().format(new Date(timestamp));
    }
}
