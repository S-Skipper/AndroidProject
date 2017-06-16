package com.dtt.chemlab.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.dtt.chemlab.managers.NewsManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtil {
	
	private static String ID = MyApplication.ID;
	private static String PW = MyApplication.PW;
	
	
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
					connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes(argvs);
					
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
	
	//调用webservice的方法
	public static String requestWebservice(final String endPoint,String methodName,
			          String [] parameters,String [] parValues){
		if (!isNetworkAvailable()) {
			//Toast.makeText(MyApplication.getContext(), "Network is unavailable!", Toast.LENGTH_SHORT).show();
			return null;
		}
		String response="";
		String namespace = "http://tempuri.org/";
		
		String soapAction = namespace+methodName;

		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(namespace, methodName);

		// 设置需调用WebService接口需要传入的参数
		if (!endPoint.equals(NewsManager.address)) {
			rpc.addProperty("ID", ID);
			rpc.addProperty("PW", PW);
		}
		
		if (parameters!=null) {
			for (int i = 0; i < parameters.length; i++) {
				/*String param = parameters[i].toString();
				String value = parValues[i].toString();*/
				rpc.addProperty(parameters[i], parValues[i]);
			}
		}
		
		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Log.d("MainActivity", envelope.bodyIn.toString());
		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		//Log.d("MainActivity", object.getProperty(0).toString());
		//response = object.getProperty(0).toString();
		if (object==null) {
			response = null;
		}else {
			response = object.getProperty(0).toString();
		}
		//Log.d("Tag", response);
		return response;
	}
}
