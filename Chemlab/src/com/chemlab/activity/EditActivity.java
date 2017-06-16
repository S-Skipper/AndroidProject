package com.chemlab.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chemlab.R;
import com.chemlab.fragment.ContactFragment;
import com.chemlab.util.HttpCallbackListener;
import com.chemlab.util.HttpUtil;

public class EditActivity extends Activity implements OnClickListener{

	private String linkString;
	private String typeString;
	private String keyString;
	private String valueString;
	private EditText keyEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit);
		
		linkString = (String) getIntent().getStringExtra("link");
		typeString = (String) getIntent().getStringExtra("type");
		keyString = (String) getIntent().getStringExtra("key");
		valueString = (String) getIntent().getStringExtra("value");
		
		((TextView) findViewById(R.id.textview_title)).setText("修改");
		((Button)findViewById(R.id.button_back)).setOnClickListener(this);
		findViewById(R.id.edit_submit).setOnClickListener(this);
		keyEdit = (EditText) findViewById(R.id.edit_value);
		keyEdit.setText(valueString);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_back:
			finish();
			break;
		case R.id.edit_submit:
			valueString = keyEdit.getText().toString().trim();
			String argvs = HttpUtil.createJsonStr(typeString, ("\"update_name\":\""+keyString+"\",\"value\":\""+valueString+"\","));
			HttpUtil.sendHttpRequest(linkString, argvs, new HttpCallbackListener() {
				
				@Override
				public void onFinish(String response) {
					try {
						JSONObject responseObject = new JSONObject(response);

						if (responseObject.getString("error").equals("0")) {
							
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(EditActivity.this, "信息修改成功!", Toast.LENGTH_SHORT).show();
									ContactFragment.updateContacts();
									backActivity(keyEdit.getText().toString());
								}
							});
						}
					} catch (Exception e) {
						e.printStackTrace();
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(EditActivity.this, "出现异常!", Toast.LENGTH_SHORT).show();
							}
						});
					}
					
				}
				
				@Override
				public void onError(Exception e) {
					Toast.makeText(EditActivity.this, "信息修改失败!", Toast.LENGTH_SHORT).show();
				}
			});
			break;
		default:
			break;
		}
		
	}
	private void backActivity(String value){
		Intent intent = new Intent();
		intent.putExtra("data_return", value);
		setResult(RESULT_OK,intent);
		finish();
	}
	
	/*@Override
	public void onBackPressed() {
		backActivity(keyEdit.getText().toString());
	}*/
}
