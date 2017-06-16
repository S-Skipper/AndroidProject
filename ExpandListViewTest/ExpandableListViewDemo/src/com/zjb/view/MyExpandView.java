package com.zjb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ExpandableListView;

public class MyExpandView extends ExpandableListView{

	public MyExpandView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyExpandView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("TAG","group-->onInterceptTouchEvent-->down");
			break;
		case MotionEvent.ACTION_UP:
			Log.i("TAG","group-->onInterceptTouchEvent-->up");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("TAG","group-->onInterceptTouchEvent-->move");
			break;
		}
		
		
		return false;
	}

}
