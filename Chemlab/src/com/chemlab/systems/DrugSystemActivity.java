package com.chemlab.systems;

import com.chemlab.R;
import com.chemlab.activity.SearchActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DrugSystemActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_drug);
		
		((TextView) findViewById(R.id.textview_title)).setText("药品管理系统");
		findViewById(R.id.button_back).setOnClickListener(this);
		findViewById(R.id.manage_drug).setOnClickListener(this);
		findViewById(R.id.manage_mix).setOnClickListener(this);
		findViewById(R.id.manage_drug_in_out).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.manage_drug:
			ResultListActivity.actionStart(DrugSystemActivity.this, SearchActivity.SEARCH_DRUG);
			break;
		case R.id.manage_mix:
			ResultListActivity.actionStart(DrugSystemActivity.this, SearchActivity.SEARCH_MIX);
			break;
		case R.id.manage_drug_in_out:
			Intent intent = new Intent(DrugSystemActivity.this, DrugInOutActivity.class);
			startActivity(intent);
			break;
		case R.id.button_back:
			finish();
			break;
		default:
			break;
		}
	}

}
