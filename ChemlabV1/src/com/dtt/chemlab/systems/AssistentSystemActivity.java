package com.dtt.chemlab.systems;

import com.dtt.chemlab.R;
import com.dtt.chemlab.activity.ContactActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AssistentSystemActivity extends Activity {
	
	private TextView titleText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_assistent);
		
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("学生助理系统");
		
		findViewById(R.id.assist_view_contacts).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AssistentSystemActivity.this, ContactActivity.class);
				startActivity(intent);
			}
		});
	}

}
