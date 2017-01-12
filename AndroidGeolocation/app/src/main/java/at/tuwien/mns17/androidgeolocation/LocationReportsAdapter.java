package at.tuwien.mns17.androidgeolocation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import at.tuwien.mns17.androidgeolocation.location_report.LocationReport;


class LocationReportsAdapter extends RecyclerView.Adapter<LocationReportsAdapter.ViewHolder> {

    private static final String TAG = LocationReportsAdapter.class.getName();

    private LocationReportSelectionListener locationReportSelectionListener;
    private List<LocationReport> locationReports;

    private LocationReportsAdapter() {

    }

    public static LocationReportsAdapter create() {
        return new LocationReportsAdapter();
    }

    public LocationReportsAdapter with(LocationReportSelectionListener locationReportSelectionListener) {
        this.locationReportSelectionListener = locationReportSelectionListener;
        return this;
    }

    public LocationReportsAdapter update(List<LocationReport> locationReports) {
        Log.d(TAG, "Updated with " + locationReports.size() + " location reports");

        this.locationReports = locationReports;
        notifyDataSetChanged();

        return this;
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
        holder.title.setText(locationReport.getName());
        holder.subtitle.setText(locationReport.getTime());
    }

    @Override
    public int getItemCount() {
        return locationReports.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView title;
        final TextView subtitle;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.title = (TextView) view.findViewById(R.id.title);
            this.subtitle = (TextView) view.findViewById(R.id.subtitle);
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
