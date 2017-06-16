package com.dtt.fragment.news;

import com.dtt.fragment.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_main_layout);
	}
}
