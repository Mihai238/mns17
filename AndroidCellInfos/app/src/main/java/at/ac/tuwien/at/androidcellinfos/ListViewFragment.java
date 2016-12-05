package at.ac.tuwien.at.androidcellinfos;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import at.ac.tuwien.at.androidcellinfos.service.CellModel;
import at.ac.tuwien.at.androidcellinfos.service.CellModelWatcher;
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

        View rootview = getView();
        if (rootview != null && this.activeContainer == null && this.neighbourContainer == null) {
            this.activeContainer = (LinearLayout) rootview.findViewById(R.id.active_container);
            this.neighbourContainer = (LinearLayout) rootview.findViewById(R.id.neighbour_container);
        } else {
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

                        LinearLayout group = new LinearLayout(getActivity());

                        // Create the main View
                        group.setOrientation(LinearLayout.VERTICAL);
                        TextView cellId = new TextView(getActivity());
                        cellId.setText(model.getCellId());
                        group.addView(cellId);

                        // Create the View shown on expand
                        LinearLayout subgroup = new LinearLayout(getActivity());
                        subgroup.setOrientation(LinearLayout.VERTICAL);

                        TextView type = new TextView(getActivity());
                        type.setText(model.getType().name());
                        subgroup.addView(type);

                        TextView mobileCountryCode = new TextView(getActivity());
                        mobileCountryCode.setText(model.getMobileCountryCode());
                        subgroup.addView(mobileCountryCode);

                        TextView locationAreaCode = new TextView(getActivity());
                        locationAreaCode.setText(model.getLocationAreaCode());
                        subgroup.addView(locationAreaCode);

                        TextView mobileNetworkId = new TextView(getActivity());
                        mobileCountryCode.setText(model.getMobileNetworkId());
                        subgroup.addView(mobileNetworkId);

                        subgroup.setVisibility(View.GONE);
                        group.addView(subgroup);

                        group.setOnClickListener(v -> {
                            switch (subgroup.getVisibility()) {
                                case View.VISIBLE :
                                    subgroup.setVisibility(View.GONE);
                                    break;
                                case View.GONE :
                                    subgroup.setVisibility(View.VISIBLE);
                                    break;
                                default:
                                    Log.w(TAG, "Details View was in unexpected state: " + subgroup.getVisibility());
                                    break;
                            }
                        });


                        Log.i(TAG, "Inserting Cell with id " + model.getCellId());

                        if (model.getState() == CellModel.CellState.ACTIVE) {
                            this.activeContainer.addView(group);
                        } else {
                            this.neighbourContainer.addView(group);
                        }

                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();

        cellModelWatcher.close();
    }
}
