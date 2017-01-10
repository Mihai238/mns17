package at.tuwien.mns17.androidgeolocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import at.tuwien.mns17.androidgeolocation.report.Report;

public class MainActivity extends AppCompatActivity
        implements ReportSelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, ReportListFragment.newInstance())
                .commit();
    }

    @Override
    public void onReportSelected(Report report) {
        //TODO: change fragment to report details
        Log.d("Main", "onReportSelected: " + report);
    }
}
