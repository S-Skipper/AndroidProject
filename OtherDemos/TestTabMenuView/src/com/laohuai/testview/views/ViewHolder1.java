package com.laohuai.testview.views;

import android.view.LayoutInflater;
import android.view.View;

import com.laohuai.testview.R;

public class ViewHolder1 {

	private LayoutInflater inflater;
	private View view;

	public ViewHolder1(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	public View getView() {
		view = inflater.inflate(R.layout.view1, null);
		return view;
	}

}
