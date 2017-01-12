package at.tuwien.mns17.androidgeolocation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import at.tuwien.mns17.androidgeolocation.location_report.LocationReport;
import at.tuwien.mns17.androidgeolocation.location_report.LocationReportRepository;


class LocationReportsAdapter extends RecyclerView.Adapter<LocationReportsAdapter.ViewHolder> {

    private LocationReportSelectionListener locationReportSelectionListener;
    private List<LocationReport> locationReports;

    LocationReportsAdapter(LocationReportRepository locationReportRepository,
                           LocationReportSelectionListener locationReportSelectionListener) {
        this.locationReportSelectionListener = locationReportSelectionListener;
        this.locationReports = locationReportRepository.findAll();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_report_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocationReport locationReport = locationReports.get(position);

        holder.view.setOnClickListener(new LocationReportSelectionListenerAdapter(locationReport));
        holder.textView.setText(locationReport.toString());
    }

    @Override
    public int getItemCount() {
        return locationReports.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView textView;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textView = (TextView) view.findViewById(android.R.id.text1);
        }
    }

    private class LocationReportSelectionListenerAdapter implements View.OnClickListener {

        private final LocationReport locationReport;

        private LocationReportSelectionListenerAdapter(LocationReport locationReport) {
            this.locationReport = locationReport;
        }

        @Override
        public void onClick(View view) {
            locationReportSelectionListener.onLocationReportSelected(locationReport);
        }
    }
}
