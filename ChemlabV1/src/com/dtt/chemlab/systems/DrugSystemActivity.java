package com.dtt.chemlab.systems;

import com.dtt.chemlab.R;
import com.dtt.chemlab.activity.SearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class DrugSystemActivity extends Activity {

	private TextView titleText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_drug);
		
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("药品管理系统");
		
		findViewById(R.id.drug_query).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DrugSystemActivity.this, SearchActivity.class);
				startActivity(intent);
			}
		});
	}

}
