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

public class DrugDetailActivity extends Activity {

	private InfoDispView drugName;
	private InfoDispView drugCAS;
	private InfoDispView drugAlt;
	private InfoDispView drugEngName;
	private InfoDispView drugFormula;
	private InfoDispView drugWeight;
	private InfoDispView drugDanger;
	private InfoDispView drugCount;
	private InfoDispView drugStandard;
	private InfoDispView drugDetal;
	private InfoDispView drugEditor;
	private InfoDispView drugEditTime;

	private String drugString;

	public static void actionStart(Context context, String drug) {
		Intent intent = new Intent(context, DrugDetailActivity.class);
		intent.putExtra("drug", drug);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.system_drug_detail);

		drugString = getIntent().getStringExtra("drug");

		((TextView) findViewById(R.id.textview_title)).setText("药品详情");
		findViewById(R.id.button_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

		findViewById(R.id.view_drug_loc).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						DrugLocActivity.actionStart(DrugDetailActivity.this, drugString);
					}
				});

		initDrugInfoViews();
		updateDrugInfo();
	}

	private void initDrugInfoViews() {
		drugName = (InfoDispView) findViewById(R.id.drug_name);
		drugCAS = (InfoDispView) findViewById(R.id.drug_cas);
		drugAlt = (InfoDispView) findViewById(R.id.drug_alt);
		drugEngName = (InfoDispView) findViewById(R.id.drug_englishname);
		drugFormula = (InfoDispView) findViewById(R.id.drug_formula);
		drugWeight = (InfoDispView) findViewById(R.id.drug_weight);
		drugDanger = (InfoDispView) findViewById(R.id.drug_danger);
		drugCount = (InfoDispView) findViewById(R.id.drug_count);
		drugStandard = (InfoDispView) findViewById(R.id.drug_standard);
		drugDetal = (InfoDispView) findViewById(R.id.drug_detail);
		drugEditor = (InfoDispView) findViewById(R.id.drug_editor);
		drugEditTime = (InfoDispView) findViewById(R.id.drug_edittime);

		drugName.setTitleText("药品名");
		drugCAS.setTitleText("CAS");
		drugAlt.setTitleText("药品别名");
		drugEngName.setTitleText("英文名");
		drugFormula.setTitleText("分子式");
		drugWeight.setTitleText("分子量");
		drugDanger.setTitleText("危险性");
		drugCount.setTitleText("数量");
		drugStandard.setTitleText("单位");
		drugDetal.setTitleText("详细");
		drugEditor.setTitleText("编辑人");
		drugEditTime.setTitleText("编辑时间");
		
		drugName.setContentText(drugString);
	}

	private void updateDrugInfo() {
		String httpLink = HttpUtil.ADDRESS_DRUG_HANDLER;
		String argvs = HttpUtil.createJsonStr("GetDrugDetail", 
				"\"drug\":\""+ drugString + "\",");

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
						 * [{ "drug_name":"环己烷-乙醇", "drug_another_name":"a",
						 * "drug_Englishname":"b", "fen_zi_shi":"c",
						 * "fen_zi_liang":"1", "CAS":"1", "details":"2",
						 * "dangerous":"一般药品", "counting":"0", "standard":"ml",
						 * "people":"123", "edit_time":"1999-3-12 1:01:01" }]}
						 */
						if (jsonObjArray.length() > 0) {
							final JSONObject drugInfo = (JSONObject) jsonObjArray
									.get(0);
							runOnUiThread(new Runnable() {
								public void run() {
									try {
										drugName.setContentText(drugInfo
												.getString("drug_name"));

										drugCAS.setContentText(drugInfo
												.getString("CAS"));
										drugAlt.setContentText(drugInfo
												.getString("drug_another_name"));
										drugEngName.setContentText(drugInfo
												.getString("drug_Englishname"));
										drugFormula.setContentText(drugInfo
												.getString("fen_zi_shi"));
										drugWeight.setContentText(drugInfo
												.getString("fen_zi_liang"));
										drugDanger.setContentText(drugInfo
												.getString("dangerous"));
										drugCount.setContentText(drugInfo
												.getString("counting"));
										drugStandard.setContentText(drugInfo
												.getString("standard"));
										drugDetal.setContentText(drugInfo
												.getString("details"));
										drugEditor.setContentText(drugInfo
												.getString("people"));
										drugEditTime.setContentText(drugInfo
												.getString("edit_time"));
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
