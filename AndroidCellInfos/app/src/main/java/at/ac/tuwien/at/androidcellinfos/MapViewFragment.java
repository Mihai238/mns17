package at.ac.tuwien.at.androidcellinfos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MapViewFragment extends Fragment {

    public static MapViewFragment newInstance() {
        return new MapViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(R.string.map_view_text);

        return rootView;
    }

}
