package at.ac.tuwien.at.androidcellinfos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ListViewFragment.newInstance();
            case 1:
                return MapViewFragment.newInstance();
            default:
                throw new RuntimeException("This adapter can only handle 2 items, but requested: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public String getPageTitle(int position) {
        switch (position) {
            case 0:
                return "List View";
            case 1:
                return "Map View";
            default:
                throw new RuntimeException("This adapter can only handle 2 items, but requested: " + position);
        }
    }
}
