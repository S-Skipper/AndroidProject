package com.chemlab.about;


import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends Activity implements OnClickListener{

	private BootstrapEditText emailText;
	private BootstrapEditText suggestText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);

		((TextView) findViewById(R.id.textview_title)).setText("反馈建议");
		((Button)findViewById(R.id.button_back)).setOnClickListener(this);
		findViewById(R.id.fb_submit).setOnClickListener(this);
		emailText = (BootstrapEditText) findViewById(R.id.fb_email);
		suggestText = (BootstrapEditText) findViewById(R.id.fb_suggestion);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			finish();
			break;
		case R.id.fb_submit:
			Toast.makeText(FeedbackActivity.this, emailText.getText().toString()+suggestText.getText().toString(), Toast.LENGTH_SHORT).show();
			//Toast.makeText(FeedbackActivity.this, "已成功提交，谢谢您的反馈!", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
