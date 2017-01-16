package at.tuwien.mns17.androidgeolocation.service;

import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Single;

/**
 * Created by AbfalterJakob on 16.01.2017.
 */

public class MozillaLocationService implements Serializable {
    private static String TAG = "MozillaLocationService";
    private static String HOST = "https://location.services.mozilla.com/v1/geolocate?key=<API_KEY>";
    private static String API_KEY = "test";

    private JsonObjectRequest jsonObjectRequest;

    public Single<JSONObject> fetch(List<CellInfo> cells) {
        Log.i(TAG, "Staring a Geolocation fetching");

        return Single.create(subscriber -> {
            Log.i(TAG, "Initiate Geolocation fetching");

            String endpoint = HOST.replace("<API_KEY>", API_KEY);
            /**
             * Generate POST body from cells
             */

            this.jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, endpoint, null,
                    subscriber::onSuccess,
                    error -> new ServiceException(error.getMessage()));

        });
    }
}
