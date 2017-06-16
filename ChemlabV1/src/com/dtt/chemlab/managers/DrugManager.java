package com.dtt.chemlab.managers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.dtt.chemlab.objs.Drug;
import com.dtt.chemlab.objs.Loc;
import com.dtt.chemlab.util.HttpUtil;

public class DrugManager {

	public static final String address = "http://211.66.136.32/WebService_Drug.asmx";

	public DrugManager() {
	}

	public static String getDrug(String drugName) {
		return HttpUtil.requestWebservice(address, "GetDrug",
				new String[] { "infor" }, new String[] { drugName });
	}

	public static String getDetailDrug(String CompleteDrugName) {
		return HttpUtil.requestWebservice(address, "GetDrugDetail",
				new String[] { "infor" }, new String[] { CompleteDrugName });
	}

	public static String GetDrugLoc(String CompleteDrugName) {
		return HttpUtil.requestWebservice(address, "GetDrugLoc",
				new String[] { "infor" }, new String[] { CompleteDrugName });
	}

	public static String GetDrugLocDetail(String CompleteDrugName) {
		return HttpUtil.requestWebservice(address, "GetDrugLocDetail",
				new String[] { "infor" }, new String[] { CompleteDrugName });
	}

	public static String GetDrugMix(String CompleteDrugName) {
		return HttpUtil.requestWebservice(address, "GetDrugMix",
				new String[] { "infor" }, new String[] { CompleteDrugName });
	}

	public static String GetDrugMixByName(String CompleteDrugName) {
		return HttpUtil.requestWebservice(address, "GetDrugMixByName",
				new String[] { "infor" }, new String[] { CompleteDrugName });
	}

	public static String GetDrugMix_Struct(String CompleteDrugName) {
		return HttpUtil.requestWebservice(address, "GetDrugMix_Struct",
				new String[] { "infor" }, new String[] { CompleteDrugName });
	}

	public static List<Drug> getDrugArray(String jsonData) {
		List<Drug> list = new ArrayList<Drug>();

		try {
			JSONObject jsonObject = new JSONObject(jsonData);

			for (int i = 0; i < jsonObject.length(); i++) {
				JSONObject drugInfo = jsonObject.getJSONObject("" + i);
				Log.i("Tag", drugInfo.toString());

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public static List<Loc> getDrugLocArray(String jsonData) {
		List<Loc> list = new ArrayList<Loc>();
		
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			
			for (int i = 0; i < jsonObject.length(); i++) {
				JSONObject drugInfo = jsonObject.getJSONObject(""+i);
				Log.i("Tag", drugInfo.toString());

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
