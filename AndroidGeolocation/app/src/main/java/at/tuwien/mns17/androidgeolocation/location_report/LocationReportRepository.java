package at.tuwien.mns17.androidgeolocation.location_report;

import java.util.List;

public interface LocationReportRepository {

    List<LocationReport> findAll();
}
