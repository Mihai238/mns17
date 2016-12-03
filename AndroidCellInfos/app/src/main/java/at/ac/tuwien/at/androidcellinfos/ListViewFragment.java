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
    LinearLayout neighbourContainer;


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

        if( cellModelWatcher == null ) {

            View rootview = getView();
            if( rootview != null && this.activeContainer == null && this.neighbourContainer == null ) {
                this.activeContainer = (LinearLayout) rootview.findViewById(R.id.active_container);
                this.neighbourContainer = (LinearLayout) rootview.findViewById(R.id.neighbour_container);
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
                        Log.i(TAG, "Found " + cellInfo.size() + " cells");

                        // Clearing old content

                        this.activeContainer.removeAllViews();
                        this.neighbourContainer.removeAllViews();
                        Log.i(TAG, "Deleted all previous Views");

                        // Insert new content
                        for (CellModel model : cellInfo) {
                            TextView cellId = new TextView(getActivity());
                            cellId.setText(model.getCellId());

                            Log.i(TAG, "Inserting Cell with id " + model.getCellId());

                            if (model.getState() == CellModel.CellState.ACTIVE) {
                                this.activeContainer.addView(cellId);
                            } else {
                                this.neighbourContainer.addView(cellId);
                            }

                        }
                    });
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        cellModelWatcher.close();
    }
}
