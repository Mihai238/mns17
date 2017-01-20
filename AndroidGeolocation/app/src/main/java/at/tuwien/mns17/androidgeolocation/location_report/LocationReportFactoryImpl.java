package at.tuwien.mns17.androidgeolocation.location_report;

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
import at.tuwien.mns17.androidgeolocation.model.MozillaResponse;
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
