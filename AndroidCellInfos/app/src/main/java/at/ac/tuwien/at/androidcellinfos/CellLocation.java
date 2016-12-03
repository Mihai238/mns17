package at.ac.tuwien.at.androidcellinfos;

public class CellLocation {

    private final double latitude;
    private final double longitude;

    public static CellLocation from(int mcc, int mnc, int cellid, int lac) {
        return null;
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
