package com.dtt.chemlab.fragment;

import com.dtt.chemlab.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.home_fragment, container, false);
		
		return view;
	}

}
