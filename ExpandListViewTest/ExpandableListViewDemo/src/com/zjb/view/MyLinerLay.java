package com.zjb.view;

import android.app.Service;
import android.content.Context;
import android.graphics.Rect;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyLinerLay extends LinearLayout {

	private RelativeLayout rLayout;
	private View view;
	private int viewWidth=-1;
	private float move;
	private float start;
	private boolean isRight = true;
	private int sreenWidth;

	public MyLinerLay(Context context) {
		super(context);
		WindowManager manager = (WindowManager) context
				.getSystemService(Service.WINDOW_SERVICE);
		sreenWidth = manager.getDefaultDisplay().getWidth();
		getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (viewWidth == -1) {
					viewWidth = view.getWidth();
				}
				return true;
			}
		});
	}

	public MyLinerLay(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager manager = (WindowManager) context
				.getSystemService(Service.WINDOW_SERVICE);
		sreenWidth = manager.getDefaultDisplay().getWidth();
		getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (viewWidth == -1) {
					viewWidth = view.getWidth();
				}
				return true;
			}
		});
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
			rLayout = (RelativeLayout) getChildAt(0);
			LayoutParams params = (LayoutParams) rLayout.getLayoutParams();
			params.width = sreenWidth;
			rLayout.setLayoutParams(params);
			view = getChildAt(1);
	}
	
	public void move(MotionEvent event) {
		float x = event.getRawX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.i("TAG", "move--DOWN");
			start = event.getRawX();
			break;
		case MotionEvent.ACTION_UP:
			float movea=x-start;
            if(movea!=0&&movea<-viewWidth/2f){
            	scrollTo(viewWidth,0);
            	isRight=false;
            }
            	
            else {
            	isRight=true;
            	scrollTo(0, 0);
            }
			break;
		case MotionEvent.ACTION_MOVE:
			move = x - start;
            if(isRight){			
			if (move <= 0 && move >= -viewWidth)
				scrollTo(-(int) move, 0);
            }else {
            	if(move>=0&&move<=viewWidth)
            		scrollTo(viewWidth-(int)move,0);
            }
			/*
			 * else if(move<-viewWidth) move=-viewWidth; else if(move>0) move=0;
			 */
			break;
		}
	}

}
