package at.tuwien.mns17.androidgeolocation.location_report;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//TODO: this is a dummy implementation
public class LocationReportRepositoryImpl implements LocationReportRepository {

    private static final String TAG = LocationReportRepositoryImpl.class.getName();

    private List<LocationReport> locationReports = new ArrayList<>();

    @Override
    public List<LocationReport> findAll() {
        Log.d(TAG, "Finding all location reports");

        Log.d(TAG, "Found " + locationReports.size() + " location reports");

        return locationReports;
    }

    @Override
    public void save(LocationReport report) {
        Log.d(TAG, "Saving location report: " + report);

        locationReports.add(report);

        Log.d(TAG, "Location report saved: " + report);
    }
}
