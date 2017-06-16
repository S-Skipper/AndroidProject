package com.dtt;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationActivity extends Activity implements OnClickListener {

	private  Button sendNotice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_layout);
		
		sendNotice = (Button) findViewById(R.id.send_notice);
		sendNotice.setOnClickListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_notice:
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.icon,
					"QQ空间来消息了!", System.currentTimeMillis());
			Intent intent = new Intent(this, PendingIntentActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, 
					           intent, PendingIntent.FLAG_CANCEL_CURRENT);
			notification.setLatestEventInfo(this, "QQ", "xxx赞了你的说说", pendingIntent);
			
			/*
			//通知声音
			//Uri soundUri = Uri.fromFile(new File("/system/a.wav"));
			Uri soundUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.msg);
			notification.sound=soundUri;
			
			//通知时震动
			long [] vibrates = {0,1000,1000,1000};
			notification.vibrate = vibrates;
			
			//通知时亮LED灯
			notification.ledARGB=Color.GREEN;
			notification.ledARGB=1000;
			notification.ledOffMS=1000;
			notification.flags=Notification.FLAG_SHOW_LIGHTS;
			
			//Default
			notification.defaults=Notification.DEFAULT_ALL;
			*/
			
			manager.notify(1, notification);
			break;
		default:
			break;
		}
	}

}
