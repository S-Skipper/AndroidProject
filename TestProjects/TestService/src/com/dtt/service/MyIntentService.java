package com.dtt.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

	public MyIntentService() {
		super("MyIntentService");
	}

	public MyIntentService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("MyIntentService", "Thread id is: "+Thread.currentThread().getId());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("MyIntentService", "onDestroy in MyIntentService");

	}
}
