package at.tuwien.mns17.androidgeolocation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.tuwien.mns17.androidgeolocation.location_report.LocationReport;
import at.tuwien.mns17.androidgeolocation.location_report.LocationReportEmailSender;
import at.tuwien.mns17.androidgeolocation.location_report.LocationReportEmailSenderImpl;

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
        toolbar.setTitle(locationReport.getName());

        TextView timestampView = (TextView) view.findViewById(R.id.timestamp);
        timestampView.setText(locationReport.getTime());


        TextView gpsView = (TextView) view.findViewById(R.id.gps);
        TextView gpsAccuracyView = (TextView) view.findViewById(R.id.gps_accuracy);
        if (locationReport.getGPS() != null) {
            gpsView.setText(String.format("%s, %s",
                    locationReport.getGPS().getLatitude(),
                    locationReport.getGPS().getLongitude()));

            gpsAccuracyView.setText(locationReport.getGPS().getAccuracy() + " m");
        } else {
            gpsView.setText("Not available");
            gpsAccuracyView.setText("Not available");
        }

        TextView locationView = (TextView) view.findViewById(R.id.location);
        TextView locationAccuracyView = (TextView) view.findViewById(R.id.location_accuracy);
        if (locationReport.getMozillaResponse() != null) {
            locationView.setText(String.format("%s, %s",
                    locationReport.getMozillaResponse().getLat(),
                    locationReport.getMozillaResponse().getLng()));

            locationAccuracyView.setText(locationReport.getMozillaResponse().getAccuracy() + " m");
        } else {
            locationView.setText("Not available");
            locationAccuracyView.setText("Not available");
        }

        TextView compareView = (TextView) view.findViewById(R.id.location_compare);
        compareView.setText(locationReport.getDistanceBetweenMLSandGPS() + " m");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new SendMailListener());

        return view;
    }


    private class SendMailListener implements View.OnClickListener {

        private LocationReportEmailSender locationReportEmailSender = new LocationReportEmailSenderImpl();

        @Override
        public void onClick(View view) {
            locationReportEmailSender.sendEmail(view.getContext(), locationReport);
        }
    }
}
