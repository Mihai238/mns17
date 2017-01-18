package at.tuwien.mns17.androidgeolocation.model;


import android.net.wifi.ScanResult;

/**
 * Created by AbfalterJakob on 18.01.2017.
 */

public class WifiModel {
    private final String macAddress;
    private final int frequency;
    private final int signalStrength;
    private final String ssid;

    public WifiModel(String macAddress, int frequency, int signalStrength, String ssid) {
        this.macAddress = macAddress;
        this.frequency = frequency;
        this.signalStrength = signalStrength;
        this.ssid = ssid;
    }

    public static WifiModel fromScanResult(ScanResult scanResult) {
        return new WifiModel(scanResult.BSSID, scanResult.frequency, scanResult.level, scanResult.SSID);
    }

    public String getMacAddress() {
        return macAddress;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public String getSsid() {
        return ssid;
    }
}
