package at.tuwien.mns17.androidgeolocation;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import at.tuwien.mns17.androidgeolocation.location_report.LocationReport;

public class MainActivity extends AppCompatActivity
        implements LocationReportSelectionListener, FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        onBackStackChanged();

        if (savedInstanceState != null) return;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ReportListFragment.newInstance())
                .commit();
    }

    @Override
    public void onLocationReportSelected(LocationReport locationReport) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container, LocationReportDetailsFragment.newInstance(locationReport))
                .addToBackStack("location-report-details")
                .commit();
    }

    @Override
    public void onBackStackChanged() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;

        actionBar.setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return getSupportFragmentManager().popBackStackImmediate();
    }
}
