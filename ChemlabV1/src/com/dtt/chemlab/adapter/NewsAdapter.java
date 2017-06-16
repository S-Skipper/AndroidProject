package com.dtt.chemlab.adapter;

import java.util.List;

import com.dtt.chemlab.objs.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<News>{

	private int resourceId;

	public NewsAdapter(Context context, int resource, List<News> objects) {
		super(context, resource, objects);
		this.resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// return super.getView(position, convertView, parent);
		View view = null;

		//News news = getItem(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		return view;
	}

	class ViewHolder {
		ImageView newsImage;
		TextView newsText;
	}
}
