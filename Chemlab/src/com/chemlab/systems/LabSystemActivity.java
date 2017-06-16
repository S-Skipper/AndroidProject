package com.chemlab.systems;

import com.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class LabSystemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_lab);

		((TextView) findViewById(R.id.textview_title)).setText("开放性实验系统");

	}

}
