package at.ac.tuwien.at.androidcellinfos.service;

import org.simpleframework.xml.Attribute;

public class Cell {
    @Attribute(required = false)
    double lat;
    @Attribute(required = false)
    double lon;
    @Attribute(required = false)
    int mcc;
    @Attribute(required = false)
    int mnc;
    @Attribute(required = false)
    int lac;
    @Attribute(required = false)
    int cellid;
    @Attribute(required = false)
    int averageSignalStrength;
    @Attribute(required = false)
    int range;
    @Attribute(required = false)
    int samples;
    @Attribute(required = false)
    int changeable;
    @Attribute(required = false)
    String radio;
    @Attribute(required = false)
    int rnc;
    @Attribute(required = false)
    int cid;

    @Override
    public String toString() {
        return "Cell{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", mcc=" + mcc +
                ", mnc=" + mnc +
                ", lac=" + lac +
                ", cellid=" + cellid +
                ", averageSignalStrength=" + averageSignalStrength +
                ", range=" + range +
                ", samples=" + samples +
                ", changeable=" + changeable +
                ", radio='" + radio + '\'' +
                ", rnc=" + rnc +
                ", cid=" + cid +
                '}';
    }
}
