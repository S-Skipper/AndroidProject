package com.dtt;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMSActivity extends Activity {

	private TextView sender;
	private TextView content;
	private EditText receiver;
	private EditText msgInput;
	private Button sendButton;
	
	private IntentFilter receiveFilter;
	private IntentFilter sendFilter;
	private SMSReceiver messageReceiver;
	private SendStatusReceiver sendStatusReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_layout);
		
		sender = (TextView) findViewById(R.id.sender);
		content = (TextView) findViewById(R.id.content);
		receiver = (EditText) findViewById(R.id.to);
		msgInput = (EditText) findViewById(R.id.msg_input);
		sendButton = (Button) findViewById(R.id.send);
		
		//--------------------- receive message ------------------//
		receiveFilter = new IntentFilter();
		receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		messageReceiver = new SMSReceiver();
		registerReceiver(messageReceiver, receiveFilter);
		
		
		//---------------------- send message -------------------//
		sendFilter = new IntentFilter();
		sendFilter.addAction("SENT_SMS_ACTION");
		sendStatusReceiver = new SendStatusReceiver();
		registerReceiver(sendStatusReceiver, sendFilter);
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SmsManager smsManager = SmsManager.getDefault();
				
				Intent sentIntent = new Intent("SENT_SMS_ACTION");
				PendingIntent pi = PendingIntent.getBroadcast(SMSActivity.this, 
						0, sentIntent, 0);
				
				smsManager.sendTextMessage(receiver.getText().toString(), null,
						msgInput.getText().toString(), pi, null);
				
			}
		});
		
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(messageReceiver);
		unregisterReceiver(sendStatusReceiver);
	}
	
	class SendStatusReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if (getResultCode()==RESULT_OK) {
				Toast.makeText(context, "Send Successful!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "Send Failed!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	class SMSReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			Object [] pdus = (Object[]) bundle.get("pdus");
			
			SmsMessage [] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			}
			String address = messages[0].getOriginatingAddress();
			String fullMessage = "";
			
			for (SmsMessage mssage : messages) {
				fullMessage += mssage.getMessageBody();
			}
			
			sender.setText(address);
			content.setText(fullMessage);
		}
		
	}
}
