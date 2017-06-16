package com.listviewdemo_expandablelistview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
/**
 * expandableListView  ≈‰∆˜
 *
 */
public class MyAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<String> group;
	private List<List<String>> child;

	public MyAdapter(Context context, List<String> group,
			List<List<String>> child) {
		this.context = context;
		this.group = group;
		this.child = child;
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(childPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	/**
	 * œ‘ æ£∫group
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(group.get(groupPosition));
		holder.textView.setTextSize(25);
		holder.textView.setPadding(36, 10, 0, 10);
		return convertView;

	}
	
	/**
	 * œ‘ æ£∫child
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, null);
			holder = new ViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(child.get(groupPosition).get(childPosition));
		holder.textView.setTextSize(20);
		holder.textView.setPadding(72, 10, 0, 10);
		return convertView;
	}

	class ViewHolder {
		TextView textView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
