package com.chemlab.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.objs.Result;

public class ResultListAdapter extends ArrayAdapter<Result>{

	private int resourceId;

	public ResultListAdapter(Context context, int resource, List<Result> objects) {
		super(context, resource, objects);
		this.resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// return super.getView(position, convertView, parent);
		View view = null;

		Result result= getItem(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder.Name = (TextView) view.findViewById(R.id.text_name);
			viewHolder.prop1 = (TextView) view.findViewById(R.id.text_prop1);
			viewHolder.prop2 = (TextView) view.findViewById(R.id.text_prop2);
			
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.Name.setText(result.getName());
		viewHolder.prop1.setText(result.getProp1());
		viewHolder.prop2.setText(result.getProp2());
		
		return view;
	}

	class ViewHolder {
		TextView Name;
		TextView prop1;
		TextView prop2;
	}
}
