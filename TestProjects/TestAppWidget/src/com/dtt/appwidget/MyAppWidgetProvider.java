package com.dtt.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class MyAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				                                   R.layout.widget_layout);
		
		remoteViews.setOnClickPendingIntent(R.id.fruit_icon, pi);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		if (intent.getAction().equals("SMIE_SYSU")) {
			Bundle bundle = intent.getExtras();
			
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			remoteViews.setTextViewText(R.id.fruit_name, bundle.getString("fruit_name"));
			AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(
					context.getApplicationContext(),MyAppWidgetProvider.class), 
					remoteViews);
			
		}
	}

}
