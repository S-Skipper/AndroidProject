package com.chemlab.systems;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.chemlab.R;

public class CourseSystemActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_course);
		
		((TextView) findViewById(R.id.textview_title)).setText("课程管理系统");
	}

}
