package at.tuwien.mns17.androidgeolocation.model;

/**
 * Created by Michael on 21.01.2017.
 */

public class GPSModel {
    private double longitude;
    private double latitude;
    private float accuracy;

    public GPSModel(double longitude, double latitude, float accuracy) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
