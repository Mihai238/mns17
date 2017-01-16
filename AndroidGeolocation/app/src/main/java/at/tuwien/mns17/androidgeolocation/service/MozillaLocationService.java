package at.tuwien.mns17.androidgeolocation.service;

import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;

import org.json.JSONObject;

import java.io.File;
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

    private RequestQueue mRequestQueue;

    public MozillaLocationService() {

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(new NoCache(), network);

        // Start the queue
        mRequestQueue.start();
    }

    public Single<JSONObject> fetch(List<CellInfo> cells) {
        Log.i(TAG, "Staring a Geolocation fetching");

        return Single.create(subscriber -> {
            Log.i(TAG, "Initiate Geolocation fetching");

            String endpoint = HOST.replace("<API_KEY>", API_KEY);
            /**
             * Generate POST body from cells
             */

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, endpoint, null,
                    subscriber::onSuccess,
                    error -> subscriber.onError(new ServiceException(error.getMessage())));
            this.mRequestQueue.add(jsonObjectRequest);
        });
    }
}
