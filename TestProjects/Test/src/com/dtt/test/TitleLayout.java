package com.dtt.test;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TitleLayout extends LinearLayout {

	public TitleLayout(Context context) {
		super(context);
	}

	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
		Button titleBack = (Button)findViewById(R.id.title_back);
		Button titleGo = (Button)findViewById(R.id.title_go);
		
		titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getContext(), "Clicked Back", Toast.LENGTH_SHORT).show();
			    ((Activity)getContext()).finish();
			}
		});
		
		titleGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "Clicked Go", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
