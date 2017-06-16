package com.chemlab.systems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.util.HttpCallbackListener;
import com.chemlab.util.HttpUtil;
import com.chemlab.util.LogUtil;
import com.chemlab.view.InfoDispView;

public class DrugMixDetailActivity extends Activity {

	private InfoDispView mixName;
	private InfoDispView mixStandard;
	private InfoDispView mixAttention;
	private ListView structListView;
	
	private String mixString;
	
	public static void actionStart(Context context, String mix) {
		Intent intent = new Intent(context, DrugMixDetailActivity.class);
		intent.putExtra("mix", mix);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_mix_detail);
		
		mixString = getIntent().getStringExtra("mix");
		
		((TextView) findViewById(R.id.textview_title)).setText("试剂详情");
		findViewById(R.id.button_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mixName = (InfoDispView) findViewById(R.id.mix_name);
		mixStandard = (InfoDispView) findViewById(R.id.mix_standard);
		mixAttention = (InfoDispView) findViewById(R.id.mix_attention);
		
		mixName.setTitleText("试剂名");
		mixStandard.setTitleText("试剂单位");
		mixAttention.setTitleText("注意事项");
		
		mixName.setContentText(mixString);
		
		structListView = (ListView) findViewById(R.id.mix_struct_list);
		
		updateMixInfo();
		updateFormulaInfo();
	}
	
	private void updateMixInfo() {
		String httpLink = HttpUtil.ADDRESS_DRUG_HANDLER;
		String argvs = HttpUtil.createJsonStr("GetDrugMixByName", "\"mix\":\""
				+ mixString + "\",");

		HttpUtil.sendHttpRequest(httpLink, argvs, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				JSONObject responseObject;
				try {
					responseObject = new JSONObject(response);

					if (responseObject.getString("error").equals("0")) {
						JSONArray jsonObjArray = responseObject
								.getJSONArray("data");
						/*
						 *[{
						 *"drug_mix":"9",
						 *"attention":"",
						 *"standard":""}]}
						 *
						 */
						if (jsonObjArray.length() > 0) {
							final JSONObject mixInfo = (JSONObject) jsonObjArray
									.get(0);
							runOnUiThread(new Runnable() {
								public void run() {
									try {
										mixName.setContentText(mixInfo.getString("drug_mix"));
										mixStandard.setContentText(mixInfo.getString("standard"));
										mixAttention.setContentText(mixInfo.getString("attention"));
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							});
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void updateFormulaInfo() {
		String httpLink = HttpUtil.ADDRESS_DRUG_HANDLER;
		String argvs = HttpUtil.createJsonStr("GetDrugMix_Struct", "\"mix\":\""
				+ mixString + "\",");

		HttpUtil.sendHttpRequest(httpLink, argvs, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				JSONObject responseObject;
				try {
					responseObject = new JSONObject(response);

					if (responseObject.getString("error").equals("0")) {
						JSONArray jsonObjArray = responseObject
								.getJSONArray("data");
						/*
						 *
						 */
						if (jsonObjArray.length() > 0) {
							final JSONObject mixInfo = (JSONObject) jsonObjArray
									.get(0);
							LogUtil.d("Tag", mixInfo.toString());
							runOnUiThread(new Runnable() {
								public void run() {
								}
							});
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
	}

}
