package com.dtt.testintent;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class ThirdActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.third_layout);

		Button btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityCollector.finishAll();
			}
		});
	}

}
