package at.ac.tuwien.at.androidcellinfos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapViewFragment extends Fragment {

    private static final String TAG = "MapViewFragment";

    private CellModelWatcher cellModelWatcher;

    public static MapViewFragment newInstance() {
        return new MapViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(R.string.map_view_text);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        cellModelWatcher = new CellModelWatcher(telephonyManager);

        cellModelWatcher.watch()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .forEach(cellInfo -> Log.i(TAG, "Got: " + cellInfo));
    }

    @Override
    public void onPause() {
        super.onPause();

        cellModelWatcher.close();
    }
}
