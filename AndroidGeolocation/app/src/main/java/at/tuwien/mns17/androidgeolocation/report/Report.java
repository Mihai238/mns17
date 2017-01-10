package at.tuwien.mns17.androidgeolocation.report;

import java.util.Calendar;

public class Report {
    private Long timestamp = Calendar.getInstance().getTimeInMillis();

    public Long getTimestamp() {
        return timestamp;
    }
}
