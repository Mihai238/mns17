package at.tuwien.mns17.androidgeolocation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.tuwien.mns17.androidgeolocation.location_report.LocationReport;

public class LocationReportDetailsFragment extends Fragment {

    private static final String LOCATION_REPORT_ARG = "locationReport";

    private LocationReport locationReport;

    public static LocationReportDetailsFragment newInstance(LocationReport locationReport) {
        LocationReportDetailsFragment fragment = new LocationReportDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(LOCATION_REPORT_ARG, locationReport);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationReport = (LocationReport) getArguments().getSerializable(LOCATION_REPORT_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_report_details, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Location report");

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(locationReport.toString());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new SendMailListener());

        return view;
    }


    private class SendMailListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
