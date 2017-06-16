/**
 * 
 */
package com.dtt.chemlab.others;

import com.dtt.chemlab.R;
import com.dtt.chemlab.activity.BaseActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 我的资料Activity
 * @author 飞雪无情
 * @since 2011-3-8
 */
public class MyInfoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*TextView textView=new TextView(this);
		textView.setText("这是我的资料");
		setContentView(textView);*/
	}
	@Override
	protected void setContents() {
		//super.setContents();
		setContentView(R.layout.activity_main);
		content = findViewById(R.id.content_son);
	    menu = findViewById(R.id.menu);
	}
}
