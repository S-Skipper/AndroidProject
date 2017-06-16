package com.dtt;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

public class PendingIntentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pendingintent_layout);
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(1);
	}
}
