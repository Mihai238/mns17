package at.tuwien.mns17.androidgeolocation.location_report;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import at.tuwien.mns17.androidgeolocation.model.GPSModel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class LocationReportRepositoryImplTest {

    private LocationReportRepositoryImpl repo;

    @Test
    public void persistOne() {
        LocationReport report = report();

        repo.save(report);
        List<LocationReport> reports = repo.findAll();

        assertThat(reports, hasSize(1));
        assertThat(reports.get(0).getUUID(), is(report.getUUID()));
        assertThat(reports.get(0).getGPS().getAccuracy(), is(report.getGPS().getAccuracy()));
    }

    @Test
    public void persistAndDelete() {
        LocationReport report = report();

        repo.save(report);
        repo.delete(report);
        List<LocationReport> reports = repo.findAll();

        assertThat(reports, empty());
    }

    private LocationReport report() {
        LocationReport report = new LocationReport();
        report.setGPS(new GPSModel(2.2, 3.1, 2.1f));
        return report;
    }

    @Before
    public void setUp() {
        Context ctx = InstrumentationRegistry.getTargetContext();
        repo = new LocationReportRepositoryImpl(ctx, "pwd");
        for (LocationReport report : repo.findAll()) {
            repo.delete(report);
        }
    }

}