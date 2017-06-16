package com.chemlab.about;

import com.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class AboutApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView tv = new TextView(AboutApp.this);
		tv.setBackgroundResource(R.drawable.bg_gray);
		tv.setText("About App");
		tv.setGravity(Gravity.CENTER);
		setContentView(tv);
		
	}
	

}
