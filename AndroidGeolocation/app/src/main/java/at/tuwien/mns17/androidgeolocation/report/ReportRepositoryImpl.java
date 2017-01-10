package at.tuwien.mns17.androidgeolocation.report;

import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {

    //TODO: this is a dummy implementation
    @Override
    public List<Report> findAll() {
        List<Report> reports = new ArrayList<>();

        for (int i = 0; i < 25; ++i) {
            reports.add(new Report());
        }

        return reports;
    }
}
