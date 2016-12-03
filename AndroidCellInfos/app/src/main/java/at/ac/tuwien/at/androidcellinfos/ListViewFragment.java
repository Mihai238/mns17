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
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListViewFragment extends Fragment {

    private static final String TAG = "ListViewFragment";

    private CellModelWatcher cellModelWatcher;
    LinearLayout activeContainer;


    public static ListViewFragment newInstance() {
        return new ListViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        View rootview = getView();
        if( rootview != null ) {
            this.activeContainer = (LinearLayout) rootview.findViewById(R.id.active_container);
        }
        else {
            Log.e(TAG, "Unable to detect root view of Fragment");
        }

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        cellModelWatcher = new CellModelWatcher(telephonyManager);

        cellModelWatcher.watch()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .forEach(cellInfo -> {
                    Log.i(TAG, "Got: " + cellInfo);
                    TextView cellId = new TextView(getActivity());
                    cellId.setText(cellInfo.get(0).getCellId());
                    this.activeContainer.addView(cellId);
                });
    }

    @Override
    public void onPause() {
        super.onPause();

        cellModelWatcher.close();
    }
}
