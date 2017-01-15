package at.tuwien.mns17.androidgeolocation;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import at.tuwien.mns17.androidgeolocation.location_report.LocationReport;
import at.tuwien.mns17.androidgeolocation.location_report.LocationReportEmailSender;
import at.tuwien.mns17.androidgeolocation.location_report.LocationReportRepository;


class LocationReportsAdapter extends RecyclerView.Adapter<LocationReportsAdapter.ViewHolder> {

    private static final String TAG = LocationReportsAdapter.class.getName();

    private final LocationReportRepository locationReportRepository;
    private final LocationReportEmailSender locationReportEmailSender;
    private final LocationReportSelectionListener locationReportSelectionListener;
    private List<LocationReport> locationReports;

    public LocationReportsAdapter(LocationReportRepository locationReportRepository,
                                  LocationReportEmailSender locationReportEmailSender,
                                  LocationReportSelectionListener locationReportSelectionListener) {

        this.locationReportRepository = locationReportRepository;
        this.locationReportEmailSender = locationReportEmailSender;
        this.locationReportSelectionListener = locationReportSelectionListener;

        refresh();
    }

    public void refresh() {
        this.locationReports = locationReportRepository.findAll();

        Log.d(TAG, "Refreshed with " + locationReports.size() + " location reports");

        notifyDataSetChanged();
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

        holder.bind(locationReport);
    }

    @Override
    public int getItemCount() {
        return locationReports.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView title;
        private final TextView subtitle;
        private final View menu;
        private LocationReport locationReport;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.title = (TextView) view.findViewById(R.id.title);
            this.subtitle = (TextView) view.findViewById(R.id.subtitle);
            this.menu = view.findViewById(R.id.menu);
        }

        public void bind(LocationReport locationReport) {
            this.locationReport = locationReport;

            view.setOnClickListener(new LocationReportSelectionListenerAdapter());
            title.setText(locationReport.getName());
            subtitle.setText(locationReport.getTime());

            menu.setOnClickListener(new MoreOptionsClickListener());
        }


        private class LocationReportSelectionListenerAdapter implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                locationReportSelectionListener.onLocationReportSelected(locationReport);
            }
        }

        private class MoreOptionsClickListener implements View.OnClickListener {

            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), menu, Gravity.END);
                popup.inflate(R.menu.menu_location_report_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_send_mail) {
                            handleSendEmail();
                        } else if (item.getItemId() == R.id.action_delete) {
                            handleDelete();
                        }

                        return false;
                    }
                });

                popup.show();
            }

            private void handleDelete() {
                locationReportRepository.delete(locationReport);
                refresh();
                Snackbar.make(view, "Location report deleted", Snackbar.LENGTH_LONG).show();
            }

            private void handleSendEmail() {
                locationReportEmailSender.sendEmail(locationReport);
            }
        }
    }
}
