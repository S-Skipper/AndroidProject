package com.dtt;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private IntentFilter intentFilter;
	private NetworkChangeReceiver networkChangeReceiver;
	
	private LocalReceiver localReceiver;
	private LocalBroadcastManager localBroadcastManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver = new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver, intentFilter);
		
		Button button = (Button)findViewById(R.id.btn);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.dtt.MY_BROADCAST");
				//sendBroadcast(intent);
				sendOrderedBroadcast(intent, null);
			}
		});
		
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		intentFilter.addAction("com.dtt.LOCAL_BROADCAST");
		localReceiver=new LocalReceiver();
		localBroadcastManager.registerReceiver(localReceiver, intentFilter);
		
		Button button2 = (Button) findViewById(R.id.btn2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("com.dtt.LOCAL_BROADCAST");
				localBroadcastManager.sendBroadcast(intent);
			}
		});
		
		Button forceOffline = (Button) findViewById(R.id.btn3);
		forceOffline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.dtt.FORCE_OFFLINE");
				sendBroadcast(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(networkChangeReceiver);
		localBroadcastManager.unregisterReceiver(localReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class NetworkChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			 /* Toast.makeText(context, "Network Changed", Toast.LENGTH_SHORT)
			  .show();
			 */
			ConnectivityManager connectivityManager = (ConnectivityManager) 
					getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			
			if (networkInfo!=null && networkInfo.isAvailable()) {
				Toast.makeText(context, "Network is available", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(context, "Network is unavailable", Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	class LocalReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent arg1) {
			Toast.makeText(context, "Local Broadcast", Toast.LENGTH_SHORT).show();
			
		}
		
	}
}
