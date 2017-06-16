package com.dtt.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.dtt.httpUtil.HttpCallbackListener;
import com.dtt.httpUtil.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int SHOW_RESPONSE = 1;
	public static final int NOT_RESPONSE = 2;
	private Button sendRequest;
	private TextView responseText;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				responseText.setText(response);
				break;
			case NOT_RESPONSE:
				Toast.makeText(MainActivity.this, "Error in connecting to Network",
	                     Toast.LENGTH_SHORT).show();
				break;
			default:
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sendRequest = (Button) findViewById(R.id.send_request);
		responseText = (TextView) findViewById(R.id.response_text);
		sendRequest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Send Request With HttpURLConnection
				//sendRequestWithHttpURLConnection();
				// Send Request With HttpClient
				//sendRequestWithHttpClient();
				
				// Test Send Request With HttpUtil.sendHttpRequest
				String address = "http://www.baidu.com";
				HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
					
					@Override
					public void onFinish(String response) {
						Log.d("MainActivity", "Response Successful!");
						
						Message message = new Message();
						message.what=SHOW_RESPONSE;
						message.obj= response;
						handler.sendMessage(message);
					}
					
					@Override
					public void onError(Exception e) {
						e.printStackTrace();
						Log.d("MainActivity", "Response Error!");
					}
				});
			}

		});
	}
	

	@SuppressWarnings("unused")
	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Message message = new Message();
				try {
					//GET
					HttpClient httpClient = new DefaultHttpClient();
					//HttpGet httpGet = new HttpGet("http://www.baidu.com");
					//HttpGet httpGet = new HttpGet("http://192.168.10.10/web.xml");
					HttpGet httpGet = new HttpGet("http://192.168.10.10/web.json");
					
					HttpResponse httpResponse = httpClient.execute(httpGet);
					
					//POST
					/*HttpPost httpPost = new HttpPost("http://www.baidu.com");
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", "admin"));
					params.add(new BasicNameValuePair("password", "123456"));
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"utf-8");
					httpPost.setEntity(entity);
					httpClient.execute(httpPost);*/
					
					if (httpResponse.getStatusLine().getStatusCode()==200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						
						//Log.d("MainActivity", "EntityUtils done");
						//parseXMLWithPull(response);
						//parseXMLWithSAX(response);
						//parseJSONWithJSONObject(response);
						parseJSONWithGSON(response);
						
						//Log.d("MainActivity", "sendRequestWithHttpClient");
						message.what = SHOW_RESPONSE;
						message.obj = response.toString();
						handler.sendMessage(message);
					} 
				} catch (Exception e) {
					e.printStackTrace();
					/*message.what = NOT_RESPONSE;
					handler.sendMessage(message);*/
				}
			}
		}).start();
	}

	@SuppressWarnings("unused")
	private void sendRequestWithHttpURLConnection() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection = null;
				Message message = new Message();
				
				try {
					//URL url = new URL("http://www.baidu.com");
					URL url = new URL("http://192.168.10.10/web.xml");
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					
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
					
					//parseXMLWithPull(response.toString());
					//parseXMLWithSAX(response.toString());
					
					message.what=SHOW_RESPONSE;
					message.obj= response.toString();
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
					
					message.what = NOT_RESPONSE;
					handler.sendMessage(message);
				} finally {
					if (connection!=null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	@SuppressWarnings("unused")
	private void parseXMLWithSAX(String xmlData) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			
			ContentHandler handler = new ContentHandler();
			xmlReader.setContentHandler(handler);
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void parseXMLWithPull(String xmlData) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlData));
			int eventType = xmlPullParser.getEventType();
			
			String id = "";
			String name = "";
			String version = "";
			//Log.d("MainActivity", "parseXMLWithPull");
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = xmlPullParser.getName();
				
				switch (eventType) {
				case XmlPullParser.START_TAG: {
					if ("id".equals(nodeName)) {
						id = xmlPullParser.nextText();
					} else if ("name".equals(nodeName)) {
						name = xmlPullParser.nextText();
					} else if ("version".equals(nodeName)) {
						version = xmlPullParser.nextText();
					}
					break;
				}
				case XmlPullParser.END_TAG: {
					if ("app".equals(nodeName)) {
						Log.d("MainActivity", "id is " + id);
						Log.d("MainActivity", "name is " + name);
						Log.d("MainActivity", "version is " + version);
					}
					break;
				}
				default:
					break;
				}
				
				eventType =xmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseJSONWithGSON(String jsonData) {
		Gson gson = new Gson();
		List<App> appList = gson.fromJson(jsonData, new 
				                     TypeToken<List<App>>(){}.getType());
		
		for (App app : appList) {
			Log.d("MainActivity", "App id is "+app.getId());
			Log.d("MainActivity", "App name is "+app.getName());
			Log.d("MainActivity", "App version is "+app.getVersion());
		}
	}


	@SuppressWarnings("unused")
	private void parseJSONWithJSONObject(String jsonData) {
		try {
			JSONArray jsonArray = new JSONArray(jsonData);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String id = jsonObject.getString("id");
				String name = jsonObject.getString("name");;
				String version = jsonObject.getString("version");;
				
				Log.d("MainActivity", "App id is "+id);
				Log.d("MainActivity", "App name is "+name);
				Log.d("MainActivity", "App version is "+version);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
