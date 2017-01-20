package at.tuwien.mns17.androidgeolocation.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jakob on 20.01.2017.
 */

public class MozillaResponse {
    private double lat;
    private double lng;
    private String fallback;
    private int accuracy;

    public static MozillaResponse fromMozillaResponse(JSONObject jsonObject) throws JSONException {
        return new MozillaResponse(jsonObject.getJSONObject("location").getDouble("lat"), jsonObject.getJSONObject("location").getDouble("lng"), jsonObject.getString("fallback"), (int) jsonObject.getInt("accuracy"));
    }

    public MozillaResponse(double lat, double lng, String fallback, int accuracy) {
        this.lat = lat;
        this.lng = lng;
        this.fallback = fallback;
        this.accuracy = accuracy;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
