package com.dtt.service;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button startService;
	private Button stopService;
	private Button bindService;
	private Button unbindService;
	private Button startIntentService;
	private Button startLongRunningService;
	private Button stopLongRunningService;
	
	private MyService.DownloadBinder downloadBinder;
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downloadBinder = (MyService.DownloadBinder) service;
			downloadBinder.startDownload();
			downloadBinder.getProgress();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService = (Button) findViewById(R.id.start_service);
		stopService = (Button) findViewById(R.id.stop_service);
		bindService = (Button) findViewById(R.id.bind_service);
		unbindService = (Button) findViewById(R.id.unbind_service);
		startIntentService = (Button) findViewById(R.id.start_intent_service);
		startLongRunningService = (Button) findViewById(R.id.start_timer_service);
		stopLongRunningService = (Button) findViewById(R.id.stop_timer_service);
		
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		bindService.setOnClickListener(this);
		unbindService.setOnClickListener(this);
		startIntentService.setOnClickListener(this);
		startLongRunningService.setOnClickListener(this);
		stopLongRunningService.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_service:
			Intent startIntent = new Intent(this, MyService.class);
			startService(startIntent);
			break;
		case R.id.stop_service:
			Intent stopIntent = new Intent(this, MyService.class);
			stopService(stopIntent);
			break;
		case R.id.bind_service:
			Intent bindIntent = new Intent(this, MyService.class);
			bindService(bindIntent, connection, BIND_AUTO_CREATE);
			break;
		case R.id.unbind_service:
			unbindService(connection);
			break;
		case R.id.start_intent_service:
			Log.d("MainActivity", "Thread id is: "+Thread.currentThread().getId());
			
			Intent intentService = new Intent(this, MyIntentService.class);
			startService(intentService);
			break;
		case R.id.start_timer_service:
			Intent intent1 = new Intent(this, LongRunningService.class);
			startService(intent1);
			break;
		case R.id.stop_timer_service:
			Intent intent2 = new Intent(this, LongRunningService.class);
			stopService(intent2);
			break;
		default:
			break;
		}
	}

	/*	@Override
	protected void onDestroy() {
		super.onDestroy();
		Intent stopIntent = new Intent(this, MyService.class);
		stopService(stopIntent);
	}*/
	
}
