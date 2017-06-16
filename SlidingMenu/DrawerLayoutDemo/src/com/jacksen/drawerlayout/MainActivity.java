package com.jacksen.drawerlayout;

import com.jacksen.drawerlayout.demo1.FirstDemoActivity;
import com.jacksen.drawerlayout.demo2.SecondDemoActivity;
import com.jacksen.drawerlayout.demo3.ThirdDemoActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * <p>
 * 抽屉布局（DrawerLayout）的使用，包括官方demo和自定义的两个demo
 * </p>
 * 
 * @since 2014年11月30日
 * @author jacksen
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//
		initBtn();
	}

	private void initBtn() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn1:
			intent.setClass(MainActivity.this, FirstDemoActivity.class);
			break;
		case R.id.btn2:
			intent.setClass(MainActivity.this, SecondDemoActivity.class);
			break;
		case R.id.btn3:
			intent.setClass(MainActivity.this, ThirdDemoActivity.class);
			break;
		}
		startActivity(intent);
	}

}
