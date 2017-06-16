/**
 * 
 */
package com.dtt.chemlab.others;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 搜索Activity
 * @author 飞雪无情
 * @since 2011-3-8
 */
public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView=new TextView(this);
		textView.setText("这是搜索");
		setContentView(textView);
	}
	
}
