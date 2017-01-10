package at.tuwien.mns17.androidgeolocation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import at.tuwien.mns17.androidgeolocation.report.Report;
import at.tuwien.mns17.androidgeolocation.report.ReportRepository;


class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {

    private ReportSelectionListener reportSelectionListener;
    private List<Report> reports;

    ReportsAdapter(ReportRepository reportRepository, ReportSelectionListener reportSelectionListener) {
        this.reportSelectionListener = reportSelectionListener;
        this.reports = reportRepository.findAll();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Report report = reports.get(position);

        holder.view.setOnClickListener(new ReportSelectionListenerAdapter(report));
        holder.textView.setText(report.toString());
    }

    @Override
    public int getItemCount() {
        return reports.size();
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

    private class ReportSelectionListenerAdapter implements View.OnClickListener {

        private final Report report;

        private ReportSelectionListenerAdapter(Report report) {
            this.report = report;
        }

        @Override
        public void onClick(View view) {
            reportSelectionListener.onReportSelected(report);
        }
    }
}
