package com.dtt.appwidget;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText nameText;
	private Button sendButton;
	private Button registerButton;
	private boolean isRegistered = false;
	
	MyBroadcastReceiver myBroadcastReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		nameText = (EditText) findViewById(R.id.name);
		sendButton = (Button) findViewById(R.id.send);
		registerButton = (Button) findViewById(R.id.register);
		
		myBroadcastReceiver = new MyBroadcastReceiver();
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//¾²Ì¬×¢²áµÄ¹ã²¥
				//Bundle bundle = new Bundle();
				//bundle.putString("fruit_name", nameText.getText().toString());
				//Intent intent = new Intent("SMIE_SYSU");
				//intent.putExtras(bundle);
				//sendBroadcast(intent);
				//finish();
				
				//¶¯Ì¬×¢²áµÃ¹ã²¥
				if (isRegistered) {
					Intent intent = new Intent("DTT_SMIE_SYSU");
					intent.putExtra("fruit_name", nameText.getText().toString());
					sendBroadcast(intent);
					//finish();
				} else {
					Toast.makeText(MainActivity.this, 
							    "Broadcast is unregistered!", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (!isRegistered) {
					registerReceiver(myBroadcastReceiver, new IntentFilter("DTT_SMIE_SYSU"));
				    registerButton.setText("UnRegister Broadcast");
				    isRegistered = true;
				} else {
					unregisterReceiver(myBroadcastReceiver);
					registerButton.setText("Register Broadcast");
				    isRegistered = false;
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
