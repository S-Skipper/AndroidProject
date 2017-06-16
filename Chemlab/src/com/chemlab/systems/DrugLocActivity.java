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
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.util.HttpCallbackListener;
import com.chemlab.util.HttpUtil;
import com.chemlab.view.InfoDispView;

public class DrugLocActivity extends Activity {
	
	private InfoDispView locID;
	private InfoDispView locPosition;
	private InfoDispView locCount;
	private InfoDispView locScatter;
	private InfoDispView locUnit;
	private InfoDispView locStandard;
	private InfoDispView locEditor;
	private InfoDispView locEditTime;

	private String drugString;

	public static void actionStart(Context context, String drug) {
		Intent intent = new Intent(context, DrugLocActivity.class);
		intent.putExtra("drug", drug);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_drug_loc);
		
		drugString = getIntent().getStringExtra("drug");
		
		((TextView) findViewById(R.id.textview_title)).setText(drugString);
		findViewById(R.id.button_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		
		initLocInfoViews();
		updateLocInfo();
	}

	private void initLocInfoViews() {
		locID = (InfoDispView) findViewById(R.id.loc_id);
		locPosition = (InfoDispView) findViewById(R.id.loc_position);
		locUnit = (InfoDispView) findViewById(R.id.loc_unit);
		locScatter = (InfoDispView) findViewById(R.id.loc_scatter);
		locCount = (InfoDispView) findViewById(R.id.loc_counting);
		locStandard = (InfoDispView) findViewById(R.id.loc_standard);
		locEditor = (InfoDispView) findViewById(R.id.loc_editor);
		locEditTime = (InfoDispView) findViewById(R.id.loc_edittime);

		locID.setTitleText("id");
		locPosition.setTitleText("位置");
		locCount.setTitleText("剩余瓶数");
		locScatter.setTitleText("零散量");
		locUnit.setTitleText("每瓶容量");
		locStandard.setTitleText("单位");
		locEditor.setTitleText("编辑人");
		locEditTime.setTitleText("编辑时间");
	}
	
	private void updateLocInfo() {
		String httpLink = HttpUtil.ADDRESS_DRUG_HANDLER;
		String argvs = HttpUtil.createJsonStr("GetDrugLoc", "\"drug\":\""
				+ drugString + "\",");

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
						 *:[{
						 *"id":"749",
						 *"drug_name":"环己烷-乙醇",
						 *"position":"1_K2",
						 *"counting":"2",
						 *"remain":"0",
						 *"each":"0",
						 *"standard":"ml",
						 *"people":"1233",
						 *"edit_time":"1999-3-31 1:01:01"
						 *}]
						 *
						 **/
						if (jsonObjArray.length() > 0) {
							final JSONObject locInfo = (JSONObject) jsonObjArray
									.get(0);
							runOnUiThread(new Runnable() {
								public void run() {
									try {
										locID.setContentText(locInfo.getString("id"));
										locPosition.setContentText(locInfo.getString("position"));
										locUnit.setContentText(locInfo.getString("each"));
										locScatter.setContentText(locInfo.getString("remain"));
										locCount.setContentText(locInfo.getString("counting"));
										locStandard.setContentText(locInfo.getString("standard"));
										locEditor.setContentText(locInfo.getString("people"));
										locEditTime.setContentText(locInfo.getString("edit_time"));
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

}
