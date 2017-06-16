package com.dtt.chemlab.adapter;

import java.util.List;

import com.dtt.chemlab.R;
import com.dtt.chemlab.objs.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactAdapter extends ArrayAdapter<Contact> {

	private int resourceId;

	public ContactAdapter(Context context, int resource, List<Contact> objects) {
		super(context, resource, objects);
		this.resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// return super.getView(position, convertView, parent);
		View view = null;

		Contact contact = getItem(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder.contactIcon = (ImageView) view
					.findViewById(R.id.contact_image);
			viewHolder.contactName = (TextView) view
					.findViewById(R.id.contact_name);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.contactIcon.setImageResource(contact.getImageId());
		viewHolder.contactName.setText(contact.getName());
		return view;
	}

	class ViewHolder {
		ImageView contactIcon;
		TextView contactName;
	}

}
