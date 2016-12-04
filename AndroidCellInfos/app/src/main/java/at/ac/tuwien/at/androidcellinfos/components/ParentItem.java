package at.ac.tuwien.at.androidcellinfos.components;

import java.util.List;

/**
 * Created by Jakob on 04.12.2016.
 */

public class ParentItem {

    private List<ChildItem> childItemList;

    public ParentItem(List<ChildItem> childItemList) {
        this.childItemList = childItemList;
    }

    public List<ChildItem> getChildItemList() {
        return childItemList;
    }

    public void setChildItemList(List<ChildItem> childItemList) {
        this.childItemList = childItemList;
    }
}
