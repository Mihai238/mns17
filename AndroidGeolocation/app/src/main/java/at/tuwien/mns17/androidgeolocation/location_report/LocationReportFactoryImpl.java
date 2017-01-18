package at.tuwien.mns17.androidgeolocation.location_report;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.tuwien.mns17.androidgeolocation.model.CellModel;
import at.tuwien.mns17.androidgeolocation.model.WifiModel;
import at.tuwien.mns17.androidgeolocation.service.MozillaLocationService;
import rx.Single;

public class LocationReportFactoryImpl implements LocationReportFactory {

    private TelephonyManager telephonyManager;
    private WifiManager wifiManager;

    public LocationReportFactoryImpl(TelephonyManager telephonyManager, WifiManager wifiManager) {
        this.telephonyManager = telephonyManager;
        this.wifiManager = wifiManager;
    }

    private static final String TAG = LocationReportRepositoryImpl.class.getName();

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
                                Log.i(TAG,"Recieved a response: " + success.toString());
                                singleSubscriber.onSuccess(locationReport);
                            },
                            Throwable::printStackTrace
                    );
        });
    }

    private List<CellModel> getCellInfoList() {
        List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
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
