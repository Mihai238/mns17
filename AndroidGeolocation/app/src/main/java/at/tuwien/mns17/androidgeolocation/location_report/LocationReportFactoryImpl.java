package at.tuwien.mns17.androidgeolocation.location_report;

import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.tuwien.mns17.androidgeolocation.model.CellModel;
import at.tuwien.mns17.androidgeolocation.model.GPSModel;
import at.tuwien.mns17.androidgeolocation.model.MozillaResponse;
import at.tuwien.mns17.androidgeolocation.model.WifiModel;
import at.tuwien.mns17.androidgeolocation.service.MozillaLocationService;
import rx.Single;

public class LocationReportFactoryImpl implements LocationReportFactory {

    private TelephonyManager telephonyManager;
    private WifiManager wifiManager;
    private LocationManager locationManager;

    public LocationReportFactoryImpl(TelephonyManager telephonyManager, WifiManager wifiManager, LocationManager locationManager) {
        this.telephonyManager = telephonyManager;
        this.wifiManager = wifiManager;
        this.locationManager = locationManager;
    }

    private static final String TAG = LocationReportFactoryImpl.class.getName();

    @Override
    public Single<LocationReport> createLocationReport() {

        return Single.create(singleSubscriber -> {
            LocationReport locationReport = new LocationReport();
            Log.d(TAG, "Creating new location report");

            List<CellModel> cells = getCellInfoList();
            List<WifiModel> wifiSpots = getWifiInfoList();
            Log.d(TAG, "Found " + cells.size() + " Cells and " + wifiSpots.size() + " Wifi HotSpots");

            MozillaLocationService locationService = new MozillaLocationService();
            locationService.fetch(cells, wifiSpots)
                    .subscribe(
                            (success) -> {
                                Log.d(TAG,"Recieved a response: " + success.toString());
                                try {
                                    MozillaResponse mozillaResponse = MozillaResponse.fromMozillaResponse(success);
                                    Log.i(TAG, "Got Mozilla API Response location " + mozillaResponse.getLat() + " " + mozillaResponse.getLng());
                                    locationReport.setMozillaResponse(mozillaResponse);
                                    locationReport.setCells(cells);
                                    locationReport.setWifiHotSpots(wifiSpots);
                                } catch (JSONException e) {
                                    Log.e(TAG, "Unable to parse MozillaResponse into Model " + e.getMessage());
                                }

                                Location gpsLocation = this.getGPSLocation();
                                if (gpsLocation != null) {
                                    Log.i(TAG, "Fetched GPS location: Longitude=" + gpsLocation.getLongitude() + "; Latitude=" + gpsLocation.getLatitude() + "; Accuracy=" + gpsLocation.getAccuracy());
                                    locationReport.setGPS(new GPSModel(gpsLocation.getLongitude(), gpsLocation.getLatitude(), gpsLocation.getAccuracy()));
                                } else {
                                    Log.w(TAG, "Unable to fetch GPS location");
                                }

                                singleSubscriber.onSuccess(locationReport);
                            },
                            Throwable::printStackTrace
                    );
        });
    }

    private Location getGPSLocation() {
        Log.i(TAG, "Fetching GPS location...");
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.i(TAG, "GPS Provider is enabled");
            try {
                Log.i(TAG, "Trying to fetch the GPS location...");
                return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } catch (SecurityException e) {
                Log.e(TAG, "SecurityException while fetching the GPS location", e);
                return null;
            }
        } else {
            Log.i(TAG, "GPS Provider is not enabled");
            return null;
        }
    }

    private List<CellModel> getCellInfoList() {
        List<CellInfo> nullableCellInfos = telephonyManager.getAllCellInfo();
        List<CellInfo> allCellInfo = nullableCellInfos != null ? nullableCellInfos : Collections.emptyList();

        List<CellModel> cells = new ArrayList<>();
        for( CellInfo c : allCellInfo ) {
            cells.add(CellModel.from(c));
        }
        return cells;
    }

    private List<WifiModel> getWifiInfoList() {
        List<ScanResult> scanResult = wifiManager.getScanResults();
        List<WifiModel> hotSpots = new ArrayList<>();
        for( ScanResult s : scanResult ) {
            hotSpots.add(WifiModel.fromScanResult(s));
        }
        return hotSpots;
    }

}
