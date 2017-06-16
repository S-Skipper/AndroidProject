package com.chemlab.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.objs.News;

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

		News news = getItem(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder.newsTitle = (TextView) view.findViewById(R.id.news_item_title);
			viewHolder.newsAuthor = (TextView) view.findViewById(R.id.news_item_author);
			viewHolder.newsDate = (TextView) view.findViewById(R.id.news_item_date);
			viewHolder.newsHit = (TextView) view.findViewById(R.id.news_item_hit);
			
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.newsTitle.setText(news.getTitle());
		viewHolder.newsAuthor.setText(news.getMember_Name());
		viewHolder.newsDate.setText(news.getDateTime());
		viewHolder.newsHit.setText(news.getHits());
		
		return view;
	}

	class ViewHolder {
		TextView newsTitle;
		TextView newsAuthor;
		TextView newsDate;
		TextView newsHit;
	}
}
