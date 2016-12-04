package at.ac.tuwien.at.androidcellinfos.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jakob on 04.12.2016.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private static final class ViewHolder {
        TextView textLabel;
    }

    private final List<ParentItem> itemList;
    private final LayoutInflater inflater;

    public ExpandableListViewAdapter(Context context, List<ParentItem> items) {
        this.inflater = LayoutInflater.from(context);
        this.itemList = items;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(groupPosition).getChildItemList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return itemList.get(groupPosition).getChildItemList().size();
    }

    @Override
    public int getGroupCount() {
        return itemList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return itemList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        View resultView = view;
        ViewHolder holder;

        if( resultView == null ) {
            resultView = inflater.inflate(android.R.layout.test_list_item, null); //TODO change layout id
            holder = new ViewHolder();
            holder.textLabel = (TextView) resultView.findViewById(android.R.id.title);
            resultView.setTag(holder);
        }
        else {
            holder = (ViewHolder) resultView.getTag();
        }

        final ParentItem item = (ParentItem) getGroup(groupPosition);

        holder.textLabel.setText(item.toString());

        return resultView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        View resultView = view;
        ViewHolder holder = new ViewHolder();

        if ( resultView == null ) {
            holder.textLabel = (TextView) resultView.findViewById(android.R.id.title); //TODO change view id
            resultView.setTag(holder);
        }
        else {
            holder = (ViewHolder) resultView.getTag();
        }

        final ChildItem item = (ChildItem) getChild(groupPosition, childPosition);
        holder.textLabel.setText(item.toString());

        return resultView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
