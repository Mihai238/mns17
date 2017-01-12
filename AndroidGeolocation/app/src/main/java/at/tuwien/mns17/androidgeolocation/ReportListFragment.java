package at.tuwien.mns17.androidgeolocation;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.tuwien.mns17.androidgeolocation.location_report.LocationReportRepositoryImpl;

public class ReportListFragment extends Fragment {

    private LocationReportSelectionListener locationReportSelectionListener;

    public static ReportListFragment newInstance() {
        return new ReportListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_report_list, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getActivity().getTitle());

        RecyclerView reportsRecyclerView = (RecyclerView) view.findViewById(R.id.location_reports_recycler_view);
        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reportsRecyclerView.setAdapter(new LocationReportsAdapter(new LocationReportRepositoryImpl(), locationReportSelectionListener));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new CreateReportListener());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LocationReportSelectionListener) {
            locationReportSelectionListener = (LocationReportSelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LocationReportSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        locationReportSelectionListener = null;
    }

    private class CreateReportListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
