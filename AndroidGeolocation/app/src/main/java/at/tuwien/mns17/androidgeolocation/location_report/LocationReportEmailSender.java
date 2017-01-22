package at.tuwien.mns17.androidgeolocation.location_report;

import android.content.Context;

public interface LocationReportEmailSender {

    void sendEmail(Context context, LocationReport report);
}
