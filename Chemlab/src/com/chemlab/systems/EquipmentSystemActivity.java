package com.chemlab.systems;

import com.chemlab.R;
import com.chemlab.activity.SearchActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class EquipmentSystemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_equipment);

		((TextView) findViewById(R.id.textview_title)).setText("仪器管理系统");
		
		findViewById(R.id.button_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		findViewById(R.id.equip_manage).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ResultListActivity.actionStart(EquipmentSystemActivity.this, SearchActivity.SEARCH_EQUIPMENT);
			}
		});
	}

}
