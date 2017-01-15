package at.tuwien.mns17.androidgeolocation.location_report;

import android.util.Log;

//TODO: this is a dummy implementation
public class LocationReportEmailSenderImpl implements LocationReportEmailSender {

    private static final String TAG = LocationReportRepositoryImpl.class.getName();

    @Override
    public void sendEmail(LocationReport report) {
        Log.d(TAG, "Sending location report per email: " + report);

        Log.d(TAG, "Location report sent: " + report);
    }
}
