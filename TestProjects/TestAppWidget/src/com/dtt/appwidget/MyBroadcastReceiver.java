package com.dtt.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				                                   R.layout.widget_layout);
		
		remoteViews.setTextViewText(R.id.fruit_name, intent.getStringExtra("fruit_name"));
		
		AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(
				context.getApplicationContext(),MyAppWidgetProvider.class), 
				remoteViews);
	}

}
