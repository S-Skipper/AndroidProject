package com.zjb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyTextView extends TextView{

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("TAG","child-->onTouchEvent-->down");
			break;
		case MotionEvent.ACTION_UP:
			Log.i("TAG","child-->onTouchEvent-->up");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("TAG","child-->onTouchEvent-->move");
			break;
		}
		return true;
	}


}
