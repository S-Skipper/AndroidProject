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
import com.chemlab.util.LogUtil;
import com.chemlab.view.InfoDispView;

public class EquipmentDetailActivity extends Activity {

	private InfoDispView equipName;
	private InfoDispView equipPrice;
	private InfoDispView equipFactory;
	private InfoDispView equipModel;
	private InfoDispView equipDetail;

	private String equipmentString;

	public static void actionStart(Context context, String equip) {
		Intent intent = new Intent(context, EquipmentDetailActivity.class);
		intent.putExtra("equip", equip);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_equipment_detail);

		equipmentString = getIntent().getStringExtra("equip");

		((TextView) findViewById(R.id.textview_title)).setText("药品详情");
		findViewById(R.id.button_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

		findViewById(R.id.view_equipment_loc).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						EquipmentLocActivity.actionStart(EquipmentDetailActivity.this, equipmentString);
					}
				});

		initDrugInfoViews();
		updateDrugInfo();
	}

	private void initDrugInfoViews() {
		equipName = (InfoDispView) findViewById(R.id.equip_name);
		equipPrice = (InfoDispView) findViewById(R.id.equip_price);
		equipFactory = (InfoDispView) findViewById(R.id.equip_factory);
		equipModel = (InfoDispView) findViewById(R.id.equip_model);
		equipDetail = (InfoDispView) findViewById(R.id.equip_detail);

		equipName.setTitleText("仪器名");
		equipPrice.setTitleText("价格");
		equipFactory.setTitleText("模型");
		equipModel.setTitleText("工厂");
		equipDetail.setTitleText("详细");
		
		equipName.setContentText(equipmentString);
	}

	private void updateDrugInfo() {
		String httpLink = HttpUtil.ADDRESS_EQUIPMENT_HANDLER;
		String argvs = HttpUtil.createJsonStr("GetEquip", 
				"\"equip_name\":\""+ equipmentString + "\",");

		LogUtil.d("Tag", argvs);
		HttpUtil.sendHttpRequest(httpLink, argvs, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				//LogUtil.d("Tag", response);
				JSONObject responseObject;
				try {
					responseObject = new JSONObject(response);

					if (responseObject.getString("error").equals("0")) {
						JSONArray jsonObjArray = responseObject
								.getJSONArray("data");
						LogUtil.d("Tag", response);
						/*
						 * {"equip_name":"12", 
						 * "model":"1", 
						 * "factory":"b", 
						 * "detail":"c", 
						 * "price":"10000"}
						 */
						if (jsonObjArray.length() > 0) {
							final JSONObject equipmentInfo = (JSONObject) jsonObjArray
									.get(0);
							runOnUiThread(new Runnable() {
								public void run() {
									try {
										equipName.setContentText(equipmentInfo.getString("equip_name"));
										equipPrice.setContentText(equipmentInfo.getString("price"));
										equipFactory.setContentText(equipmentInfo.getString("factory"));
										equipModel.setContentText(equipmentInfo.getString("model"));
										equipDetail.setContentText(equipmentInfo.getString("detail"));
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
