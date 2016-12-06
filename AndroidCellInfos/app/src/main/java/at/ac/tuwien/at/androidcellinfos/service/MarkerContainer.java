package at.ac.tuwien.at.androidcellinfos.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Michael on 06.12.2016.
 */

public class MarkerContainer implements Parcelable {
    List<Marker> markers = new ArrayList<>();

    public MarkerContainer() {}

    public List<Marker> getMarkers() {
        return Collections.unmodifiableList(markers);
    }

    public void addMarker(Marker marker) {
        this.markers.add(marker);
    }

    public void removeMarkers() {
        for (Marker marker : markers) {
            marker.remove();
        }

        this.markers.clear();
    }

    public static final Parcelable.Creator<MarkerContainer> CREATOR = new Parcelable.Creator<MarkerContainer>() {
        public MarkerContainer createFromParcel(Parcel in) {
            return new MarkerContainer(in);
        }

        public MarkerContainer[] newArray(int size) {
            return new MarkerContainer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeList(this.markers);
    }

    private MarkerContainer(Parcel in) {
        in.readList(this.markers, null);
    }
}
