package com.seatspotter.seatspotter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyLibraryListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> libraryCollections;
    private List<String> libraries;

    public MyLibraryListAdapter(Activity context, List<String> libraries,
                                 Map<String, List<String>> libraryCollections) {
        this.context = context;
        this.libraryCollections = libraryCollections;
        this.libraries = libraries;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return libraryCollections.get(libraries.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String library = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_row, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.childItem);

        item.setText(library);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return libraryCollections.get(libraries.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return libraries.get(groupPosition);
    }

    public int getGroupCount() {
        return libraries.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public void OnIndicatorClick(boolean isExpanded, int position){
    }

    public View getGroupView(int groupPosition, final boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String libraryName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_heading,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.heading);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(libraryName);

        //Group Indicator image
        ImageView groupIndicator = (ImageView) convertView.findViewById(R.id.groupIndicator);
        if (isExpanded) {
            groupIndicator.setImageResource(R.drawable.groupindicatoruparrow);
        } else {
            groupIndicator.setImageResource(R.drawable.groupindicatordownarrow);
        }
        groupIndicator.setSelected(isExpanded);
        groupIndicator.setTag(groupPosition);

        groupIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                OnIndicatorClick(isExpanded, position);
            }
        });
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
