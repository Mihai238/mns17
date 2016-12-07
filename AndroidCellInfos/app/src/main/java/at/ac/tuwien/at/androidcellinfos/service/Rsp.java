package at.ac.tuwien.at.androidcellinfos.service;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Rsp {
    @Attribute
    String stat;
    @Element(required = false)
    Cell cell;

    @Override
    public String toString() {
        return "Rsp{" +
                "stat='" + stat + '\'' +
                ", cell=" + cell +
                '}';
    }
}
