package at.tuwien.mns17.androidgeolocation.location_report;

import java.util.ArrayList;
import java.util.List;

public class LocationReportRepositoryImpl implements LocationReportRepository {

    //TODO: this is a dummy implementation
    @Override
    public List<LocationReport> findAll() {
        List<LocationReport> locationReports = new ArrayList<>();

        for (int i = 0; i < 25; ++i) {
            locationReports.add(new LocationReport());
        }

        return locationReports;
    }
}
