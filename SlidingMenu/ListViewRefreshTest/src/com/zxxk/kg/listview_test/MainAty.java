package com.zxxk.kg.listview_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainAty extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainaty);
		findViewById(R.id.btn01).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(MainAty.this, ListViewPull_DownActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn02).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(MainAty.this, ListViewPull_DownAndUPActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn03).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(MainAty.this, ListViewPullDown_NoRefreshActivity.class);
				startActivity(intent);
			}
		});
		findViewById(R.id.btn04).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(MainAty.this, ListViewPullDownAndUp_NoRefreshActivity.class);
				startActivity(intent);
			}
		});
	}

}
