package com.chemlab.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtil {
	
	public static String ID = MyApplication.ID;
	public static String PW = MyApplication.PW;
	public static String HOST = "bxw2359770225.my3w.com";
	
	public static final String ADDRESS_HOME = "http://"+HOST;
	public static final String ADDRESS_LOGIN_HANDLER = ADDRESS_HOME+"/Ashx/LoginHandler.ashx";
	public static final String ADDRESS_NEWS_HANDLER =  ADDRESS_HOME+"/Ashx/NewsHandler.ashx";
	public static final String ADDRESS_DRUG_HANDLER =  ADDRESS_HOME+"/Ashx/DrugHandler.ashx";
	public static final String ADDRESS_COURSE_HANDLER =  ADDRESS_HOME+"/Ashx/CourseHandler.ashx";
	public static final String ADDRESS_EQUIPMENT_HANDLER =  ADDRESS_HOME+"/Ashx/EquipmentHandler.ashx";
	public static final String ADDRESS_EXPERIMENT_HANDLER =  ADDRESS_HOME+"/Ashx/ExperHandler.ashx";
	public static final String ADDRESS_STUDENT_HANDLER =  ADDRESS_HOME+"/Ashx/StudentHandler.ashx";
	
	public static void sendHttpRequest(final String address,final String argvs,final HttpCallbackListener listener) {
		
		if (!isNetworkAvailable()) {
			//Toast.makeText(MyApplication.getContext(), "Network is unavailable!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection = null;
				
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					
					//GET
					/*connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					connection.setDoInput(true);
					connection.setDoOutput(true);*/
					//POST
					/*connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes(argvs);*/
					
					connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.write(argvs.getBytes());
					
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					
					while ((line=reader.readLine())!=null) {
						response.append(line);
					}
					
					if (listener!=null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if (listener!=null) {
						listener.onError(e);
					}
				} finally {
					if (connection!=null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	public static boolean isNetworkAvailable() {
		Context context = MyApplication.getContext();
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(
				Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		return networkInfo!=null&&networkInfo.isConnectedOrConnecting();
	}
	
	public static String createJsonStr(String function,String attachArgvs){
		String jsonString="json={" +
				"\"type\":\""+function+"\"," +
				attachArgvs+
				"\"id\":\""+ID+"\"," +
				"\"pw\":\""+PW+"\"}";
		return jsonString;
	}
	
	/*
	 Error
		error 0 “成功”;
		error 01 “失败，未登录 “;
		error 02 “失败，无权限”
		error 03 “失败，该药品名已存在，此操作会覆盖之前的记录，请在原药品位置修改”;
		error 04 “失败，修改药品信息失败”;
		error 05 “失败，出入库表插入失败”;
		error 06 “失败，修改药品信息失败”;
		error 07 “失败，无数据传入”;
		error 08 “失败，原药品不存在，请选择插入新药品”;
		error 09 “失败，未知原因操作失败，请重试或联系管理员”
		error 10 “失败，删除信息不存在”;
	 */
	public static String getErrorStatus(String errno){
		
		return "";
	}
}
