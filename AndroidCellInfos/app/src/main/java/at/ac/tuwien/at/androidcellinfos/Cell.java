package at.ac.tuwien.at.androidcellinfos;

import org.simpleframework.xml.Attribute;

public class Cell {
    @Attribute
    double lat;
    @Attribute
    double lon;
    @Attribute
    int mcc;
    @Attribute
    int mnc;
    @Attribute
    int lac;
    @Attribute
    int cellid;
    @Attribute
    int averageSignalStrength;
    @Attribute
    int range;
    @Attribute
    int samples;
    @Attribute
    int changeable;
    @Attribute
    String radio;
    @Attribute
    int rnc;
    @Attribute
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
