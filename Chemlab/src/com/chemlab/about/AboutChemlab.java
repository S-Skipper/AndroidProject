package com.chemlab.about;

import com.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class AboutChemlab extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(AboutChemlab.this);
		tv.setBackgroundResource(R.drawable.bg_gray);
		tv.setText("About Chemlab");
		tv.setGravity(Gravity.CENTER);
		setContentView(tv);
	}
	
}
