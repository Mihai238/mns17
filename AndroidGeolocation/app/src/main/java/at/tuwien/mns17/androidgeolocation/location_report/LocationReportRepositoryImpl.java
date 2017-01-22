package at.tuwien.mns17.androidgeolocation.location_report;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationReportRepositoryImpl implements LocationReportRepository {

    private static final String TAG = LocationReportRepositoryImpl.class.getName();

    private final SecurePreferences prefs;
    private final Gson gson = new Gson();

    public LocationReportRepositoryImpl(Context context, String password) {
        prefs = new SecurePreferences(context, password, "location_reports.xml");
    }

    @Override
    public List<LocationReport> findAll() {
        Log.d(TAG, "Finding all location reports");

        ArrayList<LocationReport> result = new ArrayList<>();

        Map<String, String> reports = prefs.getAll();
        for (Map.Entry<String, String> reportPair : reports.entrySet()) {
            LocationReport locationReport = gson.fromJson(reportPair.getValue(), LocationReport.class);
            if (locationReport != null) {
                result.add(locationReport);
            }
        }

        Log.d(TAG, "Found " + result.size() + " location reports");

        return result;
    }

    @Override
    public void save(LocationReport report) {
        Log.d(TAG, "Saving location report: " + report);

        SecurePreferences.Editor edit = prefs.edit();
        String jsonReport = gson.toJson(report);
        edit.putString(report.getUUID(), jsonReport);
        edit.commit();

        Log.d(TAG, "Location report saved: " + report);
    }

    @Override
    public void delete(LocationReport report) {
        Log.d(TAG, "Deleting location report: " + report);

        String id = report.getUUID();
        SecurePreferences.Editor edit = prefs.edit();
        edit.remove(id);
        edit.commit();

        Log.d(TAG, "Location report deleted: " + report);
    }
}
