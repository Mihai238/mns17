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

public class MapViewFragment extends Fragment {

    private static final String TAG = "MapViewFragment";

    private CellInfoProvider cellInfoProvider;

    public static MapViewFragment newInstance() {
        return new MapViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cellInfoProvider = new CellInfoProvider((TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        cellInfoProvider.getObservable()
                .forEach(cellInfo -> Log.i(TAG, "Got: " + cellInfo));

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(R.string.map_view_text);

        return rootView;
    }

}
