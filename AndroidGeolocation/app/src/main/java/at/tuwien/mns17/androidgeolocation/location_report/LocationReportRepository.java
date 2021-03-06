package at.tuwien.mns17.androidgeolocation.location_report;

import java.util.List;

public interface LocationReportRepository {

    List<LocationReport> findAll();

    void save(LocationReport report);

    void delete(LocationReport report);
}
