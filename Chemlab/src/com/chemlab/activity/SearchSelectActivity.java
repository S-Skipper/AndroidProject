package com.chemlab.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.chemlab.R;

public class SearchSelectActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search_select);

		((TextView) findViewById(R.id.textview_title)).setText("搜索选择");
		findViewById(R.id.button_back).setOnClickListener(this);

		findViewById(R.id.search_contact).setOnClickListener(this);
		findViewById(R.id.search_drug).setOnClickListener(this);
		findViewById(R.id.search_mix).setOnClickListener(this);
		findViewById(R.id.search_equipment).setOnClickListener(this);
		findViewById(R.id.search_course).setOnClickListener(this);
		findViewById(R.id.search_lab).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int type = 0;
		switch (v.getId()) {
		case R.id.search_contact:
			type = SearchActivity.SEARCH_CONTACT;
			break;
		case R.id.search_drug:
			type = SearchActivity.SEARCH_DRUG;
			break;
		case R.id.search_mix:
			type = SearchActivity.SEARCH_MIX;
			break;
		case R.id.search_equipment:
			type = SearchActivity.SEARCH_EQUIPMENT;
			break;
		case R.id.search_course:
			type = SearchActivity.SEARCH_COURSE;
			break;
		case R.id.search_lab:
			type = SearchActivity.SEARCH_LAB;
			break;
		case R.id.button_back:
			finish();
			break;
		default:
			break;
		}
		
		if (type != 0) {
			SearchActivity.actionStart(SearchSelectActivity.this, type);
		}
	}

}
