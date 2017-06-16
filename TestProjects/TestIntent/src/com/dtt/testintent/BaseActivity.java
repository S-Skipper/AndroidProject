package com.dtt.testintent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
		case R.id.about_item:
			Toast.makeText(this, "All rights (c) DTT!", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.quit_item:
			ActivityCollector.finishAll();
			break;
		case R.id.action_settings:
			Toast.makeText(this, "Settings!", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ActivityCollector.finishAll();
	}
}
