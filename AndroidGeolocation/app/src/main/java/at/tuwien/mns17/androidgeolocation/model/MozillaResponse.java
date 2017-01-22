package at.tuwien.mns17.androidgeolocation.model;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class MozillaResponse {
    private double lat;
    private double lng;
    private String fallback;
    private int accuracy;

    public static MozillaResponse fromMozillaResponse(JSONObject jsonObject) throws JSONException {
        return new MozillaResponse(
                jsonObject.getJSONObject("location").getDouble("lat"),
                jsonObject.getJSONObject("location").getDouble("lng"),
                jsonObject.has("fallback") ? jsonObject.getString("fallback") : null,
                (int) jsonObject.getInt("accuracy")
        );
    }

    private MozillaResponse(double lat, double lng, String fallback, int accuracy) {
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

    @Nullable
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

    @Override
    public String toString() {
        return "MozillaResponse{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", accuracy=" + accuracy +
                '}';
    }
}
