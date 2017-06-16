package com.dtt.dmusic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends ArrayAdapter<Music> {

	private int resourceId;
	
	public MusicAdapter(Context context, int resource, List<Music> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//return super.getView(position, convertView, parent);
		Music music = getItem(position);
		View view;
		ViewHolder viewHolder;
		
		if (convertView==null) {
			viewHolder = new ViewHolder();
			view= LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder.musicIcon = (ImageView) view.findViewById(R.id.music_icon);
			viewHolder.musicName = (TextView) view.findViewById(R.id.music_name);
			view.setTag(viewHolder);
		}
		else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.musicIcon.setImageResource(music.getImageId());
		viewHolder.musicName.setText(music.getOrder()+",  "+music.getName());
		return view;
	}
	
	class ViewHolder{
		ImageView musicIcon;
		TextView musicName;
	}
	
}
