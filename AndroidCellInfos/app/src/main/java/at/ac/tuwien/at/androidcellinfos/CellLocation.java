package at.ac.tuwien.at.androidcellinfos;

import android.util.Log;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CellLocation {

    private static final String TAG = CellLocation.class.getName();

    private static final RestTemplate rest = new RestTemplate(true);

    private static final String API_URL = "http://opencellid.org/cell/get?key=24e4bc88-695b-41e9-90d0-ef6c7b35bf9d&mcc={mcc}&mnc={mnc}&cellid={cellid}&lac={lac}";

    private final double latitude;
    private final double longitude;

    public static CellLocation from(int mcc, int mnc, int cellid, int lac) {
        Log.d(TAG, "Retrieving cell location...");

        Cell cell;
        try {
            ResponseEntity<Rsp> entity = rest.getForEntity(API_URL, Rsp.class, mcc, mnc, cellid, lac);
            Log.d(TAG, "Cell Location Response: "  + entity);

            if (entity.getStatusCode() != HttpStatus.OK) return null;

            cell = entity.getBody().cell;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }

        return new CellLocation(cell.lat, cell.lon);
    }

    public CellLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "CellLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}
