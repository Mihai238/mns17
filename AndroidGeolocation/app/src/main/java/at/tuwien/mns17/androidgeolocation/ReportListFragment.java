package at.tuwien.mns17.androidgeolocation;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.tuwien.mns17.androidgeolocation.report.ReportRepositoryImpl;

public class ReportListFragment extends Fragment {

    private ReportSelectionListener reportSelectionListener;

    public ReportListFragment() {
    }

    public static ReportListFragment newInstance() {
        return new ReportListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_list, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getActivity().getTitle());

        RecyclerView reportsRecyclerView = (RecyclerView) view.findViewById(R.id.reports_recycler_view);
        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reportsRecyclerView.setAdapter(new ReportsAdapter(new ReportRepositoryImpl(), reportSelectionListener));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new CreateReportListener());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReportSelectionListener) {
            reportSelectionListener = (ReportSelectionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        reportSelectionListener = null;
    }

    private class CreateReportListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //TODO: query location and create new location report
            Log.d("ReportList", "Create new report");
        }
    }
}
