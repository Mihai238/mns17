package at.tuwien.mns17.androidgeolocation.location_report;

import android.util.Log;

import java.util.concurrent.Callable;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocationReportFactoryImpl implements LocationReportFactory {

    private static final String TAG = LocationReportRepositoryImpl.class.getName();

    @Override
    public Single<LocationReport> createLocationReport() {
        return Single.fromCallable(new LocationReportProducer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class LocationReportProducer implements Callable<LocationReport> {

        @Override
        public LocationReport call() throws Exception {
            Log.d(TAG, "Creating new location report");

            //TODO: implement location measurement
            LocationReport locationReport = new LocationReport();

            Log.d(TAG, "Location report created: " + locationReport);

            return locationReport;
        }
    }
}
