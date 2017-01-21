package at.tuwien.mns17.androidgeolocation.location_report;

import android.location.Location;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.tuwien.mns17.androidgeolocation.model.CellModel;
import at.tuwien.mns17.androidgeolocation.model.GPSModel;
import at.tuwien.mns17.androidgeolocation.model.MozillaResponse;
import at.tuwien.mns17.androidgeolocation.model.WifiModel;

public class LocationReport implements Serializable {

    private static Long currentId = 1L;

    private Long id = currentId++;
    private Long timestamp = Calendar.getInstance().getTimeInMillis();

    private MozillaResponse mozillaResponse;
    private List<CellModel> cells;
    private List<WifiModel> wifiHotSpots;
    private GPSModel gps;

    public String getName() {
        return "Location report #" + id;
    }

    public String getTime() {
        return SimpleDateFormat.getInstance().format(new Date(timestamp));
    }

    public MozillaResponse getMozillaResponse() {
        return mozillaResponse;
    }

    public void setMozillaResponse(MozillaResponse mozillaResponse) {
        this.mozillaResponse = mozillaResponse;
    }

    public List<CellModel> getCells() {
        return cells;
    }

    public void setCells(List<CellModel> cells) {
        this.cells = cells;
    }

    public List<WifiModel> getWifiHotSpots() {
        return wifiHotSpots;
    }

    public void setWifiHotSpots(List<WifiModel> wifiHotSpots) {
        this.wifiHotSpots = wifiHotSpots;
    }

    public GPSModel getGPS() {
        return gps;
    }

    public void setGPS(GPSModel gps) {
        this.gps = gps;
    }

    public float getDistanceBetweenMLSandGPS() {
        // construct Location object with gps coordinates
        Location gpsLocation = new Location("");
        gpsLocation.setLatitude(gps.getLatitude());
        gpsLocation.setLongitude(gps.getLongitude());

        // construct Location object with mls coordinates
        Location mlsLocation = new Location("");
        mlsLocation.setLatitude(mozillaResponse.getLat());
        mlsLocation.setLongitude(mozillaResponse.getLng());

        return gpsLocation.distanceTo(mlsLocation);
    }

    @Override
    public String toString() {
        return getName() + ", at " + getTime();
    }
}
