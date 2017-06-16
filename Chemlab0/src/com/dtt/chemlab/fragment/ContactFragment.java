package com.dtt.chemlab.fragment;

import com.dtt.chemlab.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ContactFragment extends Fragment {

	private View view;
	private Button testButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.contacts_fragment, container, false);

		testButton = (Button) view.findViewById(R.id.button_contact);
		testButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "OK", 0).show();
			}
		});
		return view;
	}
}
