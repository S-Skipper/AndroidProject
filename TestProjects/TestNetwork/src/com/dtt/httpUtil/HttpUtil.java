package com.dtt.httpUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.Toast;

public class HttpUtil {

	public static void sendHttpRequest(final String address,final HttpCallbackListener listener) {
		
		if (!isNetworkAvailable()) {
			Toast.makeText(MyApplication.getContext(), "Network is unavailable!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection = null;
				
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					connection.setDoInput(true);
					connection.setDoOutput(true);
					
					//POST
					/*connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(connection.getOutputStream());
					out.writeBytes("username=admin&password=123456");*/
					
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

	private static boolean isNetworkAvailable() {
		
		return true;
	}
}
