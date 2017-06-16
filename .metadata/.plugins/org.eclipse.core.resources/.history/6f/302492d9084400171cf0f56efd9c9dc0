package com.chemlab.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.chemlab.R;
import com.chemlab.util.HttpCallbackListener;
import com.chemlab.util.HttpUtil;
import com.chemlab.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class SignResultActivity extends Activity {

	private ListView signListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sign_result);
		
		((TextView) findViewById(R.id.textview_title)).setText("签到记录");
		findViewById(R.id.button_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		
		signListView = (ListView) findViewById(R.layout.activity_sign_result);
		
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		String date_to = format.format(calendar.getTime());
		calendar.add(Calendar.DATE, -31);
		String date_from = format.format(calendar.getTime());
		
		HttpUtil.sendHttpRequest(
				HttpUtil.ADDRESS_STUDENT_HANDLER,
				HttpUtil.createJsonStr("GetSignInfoByID", "\"from\":\""
						+ date_from + "\",\"to\":\"" + date_to
						+ "\",\"find_id\":\"" + HttpUtil.ID + "\","),
				new HttpCallbackListener() {

					@Override
					public void onFinish(String response) {
						LogUtil.d("Tag", response);
					}

					@Override
					public void onError(Exception e) {
						
					}

				});
	}
}
