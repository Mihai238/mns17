package at.tuwien.mns17.androidgeolocation.service;

import android.util.Log;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import at.tuwien.mns17.androidgeolocation.model.CellModel;
import at.tuwien.mns17.androidgeolocation.model.WifiModel;
import rx.Single;

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

    public Single<JSONObject> fetch(List<CellModel> cells, List<WifiModel> wifiSpots) {
        Log.i(TAG, "Staring a Geolocation fetching");

        return Single.create(subscriber -> {
            Log.i(TAG, "Initiate Geolocation fetching");

            String endpoint = HOST.replace("<API_KEY>", API_KEY);
            JSONObject postbody = new JSONObject();
            try {
                postbody.put("cellTowers", marshellCellId(cells));
                postbody.put("wifiAccessPoints", marshellHotSpots(wifiSpots));
            } catch (JSONException e) {
                subscriber.onError(e);
            }
            Log.d(TAG, "Postbody: " + postbody.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, endpoint, postbody,
                    subscriber::onSuccess,
                    error -> subscriber.onError(new ServiceException(error.getMessage())));
            this.mRequestQueue.add(jsonObjectRequest);
        });
    }

    /**
     * Creates Json Structure required by Mozilla Location Service of Cellinfos
     * @param cellInfos List of Cells
     * @return JsonArray
     * @throws JSONException
     */
    private JSONArray marshellCellId(List<CellModel> cellInfos) throws JSONException {
        JSONArray cellTowers = new JSONArray();
        for( CellModel model : cellInfos ) {
            if( model.isValid() ) {
                JSONObject cellTower = new JSONObject();
                cellTower.put("radioType", model.getRadioType());
                cellTower.put("mobileCountryCode", Integer.parseInt(model.getMobileCountryCode()));
                cellTower.put("mobileNetworkCode", Integer.parseInt(model.getMobileNetworkId()));
                cellTower.put("locationAreaCode", Integer.parseInt(model.getLocationAreaCode()));
                cellTower.put("cellId", Integer.parseInt(model.getCellId()));
                cellTower.put("signalStrength", model.getSignalStrengthDbm());
                cellTowers.put(cellTower);
            }
        }

        return cellTowers;
    }

    /**
     * Creates Json Structure required by Mozilla Location Service for Wifi Hotspots
     * @param wifiModels List of Wifi Hot Spots
     * @return JsonArray
     */
    private JSONArray marshellHotSpots(List<WifiModel> wifiModels) throws JSONException {
        JSONArray wifiAccessPoints = new JSONArray();
        for( WifiModel w : wifiModels ) {
            if( w.getSsid().endsWith("_nomap") ) {
                // Per requirement hidden WLAN networks must be filtered
                continue;
            }

            JSONObject wifiAccessPoint = new JSONObject();
            wifiAccessPoint.put("macAddress", w.getMacAddress());
            if( w.getFrequency() > 0 ) {
                wifiAccessPoint.put("frequency", w.getFrequency());
            }
            if( w.getSignalStrength() != 0 ) {
                wifiAccessPoint.put("signalStrength", w.getSignalStrength());
            }
            wifiAccessPoint.put("ssid", w.getSsid());
            wifiAccessPoints.put(wifiAccessPoint);
        }
        return wifiAccessPoints;
    }
}
