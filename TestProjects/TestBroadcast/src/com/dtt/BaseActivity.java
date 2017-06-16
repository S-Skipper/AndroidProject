package com.dtt;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.action_settings:
			finish();
			break;
		default:
		}
		
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//ActivityCollector.finishAll();
	}
}
