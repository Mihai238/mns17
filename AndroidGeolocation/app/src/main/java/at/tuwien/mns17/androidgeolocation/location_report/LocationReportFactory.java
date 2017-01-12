package at.tuwien.mns17.androidgeolocation.location_report;

import rx.Single;

public interface LocationReportFactory {

    Single<LocationReport> createLocationReport();
}
