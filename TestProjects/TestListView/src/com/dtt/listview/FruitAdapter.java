package com.dtt.listview;

import java.util.List;

import com.dtt.testlistview.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends ArrayAdapter<Fruit> {

	private int resourceId;
	public FruitAdapter(Context context, int textViewResourceId,
			List<Fruit> objects) {
		super(context, textViewResourceId, objects);
		resourceId=textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//return super.getView(position, convertView, parent);
		Fruit fruit = getItem(position);
		View view; //=LayoutInflater.from(getContext()).inflate(resourceId, null);
		
		ViewHolder viewHolder;
		
		if (convertView==null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			
			viewHolder = new ViewHolder();
			viewHolder.fruitImage = (ImageView)view.findViewById(R.id.fruit_image);
			viewHolder.fruitName = (TextView)view.findViewById(R.id.fruit_name);
			view.setTag(viewHolder);
		}else {
			view = convertView;
			
			viewHolder=(ViewHolder)view.getTag();
		}
		
		/*ImageView fruitImage=(ImageView)view.findViewById(R.id.fruit_image);
		TextView fruitName = (TextView)view.findViewById(R.id.fruit_name);
		fruitImage.setImageResource(fruit.getImageId());
		fruitName.setText(fruit.getName());*/
		viewHolder.fruitImage.setImageResource(fruit.getImageId());
		viewHolder.fruitName.setText(fruit.getName());
		
		return view;
	}
	
	class ViewHolder{
		ImageView fruitImage;
		TextView fruitName;
	}

}
