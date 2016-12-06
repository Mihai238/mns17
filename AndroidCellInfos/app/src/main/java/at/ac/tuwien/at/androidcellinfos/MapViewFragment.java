package at.ac.tuwien.at.androidcellinfos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import at.ac.tuwien.at.androidcellinfos.service.CellModel;
import at.ac.tuwien.at.androidcellinfos.service.CellModelWatcher;
import at.ac.tuwien.at.androidcellinfos.service.MarkerContainer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapViewFragment";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final String MARKERCONTAINER_KEY = "MarkerContainerBundleKey";

    private CellModelWatcher cellModelWatcher;
    MapView mapView;
    MarkerContainer markerContainer;

    public static MapViewFragment newInstance() {
        return new MapViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_view, container, false);

        if ((savedInstanceState != null) && (savedInstanceState.getSerializable(MARKERCONTAINER_KEY) != null)) {
            this.markerContainer = savedInstanceState.getParcelable(MARKERCONTAINER_KEY);
        } else {
            this.markerContainer = new MarkerContainer();
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        View rootview = getView();
        if (rootview != null && this.mapView == null) {
            this.mapView = (MapView) rootview.findViewById(R.id.map_view);
        } else {
            Log.e(TAG, "Unable to detect root view of Fragment");
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        cellModelWatcher.close();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        cellModelWatcher = new CellModelWatcher(telephonyManager);

        // set layout for info window
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context context = getActivity();

                LinearLayout container = new LinearLayout(context);
                container.setOrientation(LinearLayout.VERTICAL);

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.HORIZONTAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                String rawStringData = marker.getSnippet();
                String[] splitStringData = rawStringData.split(";", 2);
                int signalStrength = Integer.parseInt(splitStringData[0]);
                String remainingInfo = splitStringData[1];

                ImageView icon = new ImageView(context);
                int imgID = R.drawable.signal_bars_5;
                if (signalStrength < -90) {
                    imgID = R.drawable.signal_bars_1;
                } else if (signalStrength < -85) {
                    imgID = R.drawable.signal_bars_2;
                } else if (signalStrength < -70) {
                    imgID = R.drawable.signal_bars_3;
                } else if (signalStrength < -75) {
                    imgID = R.drawable.signal_bars_4;
                }
                icon.setImageDrawable(ContextCompat.getDrawable(context, imgID));

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(remainingInfo);

                container.addView(title);
                container.addView(snippet);
                container.addView(icon);

                return container;
            }
        });

        cellModelWatcher.watch()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .forEach(cellInfo -> {
                    Log.i(TAG, "Found " + cellInfo.size() + " cells");

                    // remove old markers
                    markerContainer.removeMarkers();

                    Log.i(TAG, "Deleted all previous Markers");

                    int i = -30; // need for mocked LatLng if CellLocation is null
                    for (CellModel model : cellInfo) {
                        // retrieve cell information
                        LatLng position = new LatLng(0,i); // assign mocked LatLng to position
                        if (model.getLocation() != null) { // don't mock if CellLocation is not null
                            position = new LatLng(model.getLocation().getLatitude(), model.getLocation().getLongitude());
                        }
                        String state = model.getState() == CellModel.CellState.ACTIVE ? "Active Cell" : "Neighbour Cell";
                        String info = "Cell type: " + model.getType().name() + "\n"
                                +     "Mobile Country Code: " + model.getMobileCountryCode() + "\n"
                                +     "Location Area Code: " + model.getLocationAreaCode() + "\n"
                                +     "Mobile Network ID: " + model.getMobileNetworkId();
                        String payload = model.getSignalStrengthDbm() + ";" + info;

                        // create marker
                        MarkerOptions markerOptions = new MarkerOptions().position(position).title(state).snippet(payload);
                        Marker marker = googleMap.addMarker(markerOptions);
                        markerContainer.addMarker(marker);

                        i += 10; // need for mocked LatLng if CellLocation is null
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);

        outState.putParcelable(MARKERCONTAINER_KEY, this.markerContainer);

        super.onSaveInstanceState(outState);
    }
}
