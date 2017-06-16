package com.chemlab.activity;

import com.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ThoughtActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_thoughts);

		((TextView) findViewById(R.id.textview_title)).setText("留言板");

	}

}
