package com.dtt.location;

import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.dtt.baidumap.R;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity {

	public static final int SHOW_LOCATION = 0;
	
	private TextView positionText;
	private LocationManager locationManager;
	private String provider;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case SHOW_LOCATION:
				String currentPosition = (String) msg.obj;
				positionText.setText(currentPosition);
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_layout);
		
		positionText = (TextView) findViewById(R.id.text_position);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			Toast.makeText(this, "No location provider available!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			showLocation(location);
		}
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
		}
	}
	
	LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			showLocation(location);
		}
	};

	private void showLocation(final Location location) {
		// Show Latitude and Longitude of Address
		/*String currentPosition = "Current Position is :\n"
				+"Latitude is :  "+location.getLatitude()+"\n"
				+"Longitude is : "+location.getLongitude();
		
		positionText.setText(currentPosition);*/
		
		// Show Geocoding Formatted Address 
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					StringBuilder url = new StringBuilder();
					
					//此路不通
					/*String geoCodingAddress = "http://maps.googleapis.com/maps/api/geocode/json?";
					url.append(geoCodingAddress+"latlng=");
					url.append(location.getLatitude()).append(",");
					url.append(location.getLongitude()).append("&sensor=false");*/
					
					String geoCodingAddress = "http://nominatim.openstreetmap.org/reverse?format=json";
					url.append(geoCodingAddress);
					url.append("&lat="+location.getLatitude());
					url.append("&lon="+location.getLongitude())
					   .append("&addressdetails=1");
					//Log.d("MainActivity", url.toString());
					//Http Request
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url.toString());
					//消息头中指定语言，确保返回中文数据
					httpGet.addHeader("Accept-Language", "zh-CN");
					//Log.d("MainActivity", "httpGet.addHeader");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					
					//Log.d("MainActivity", ""+httpResponse.getStatusLine().getStatusCode());
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						
						JSONObject jsonObject = new JSONObject(response);
						//JSONArray resultArray = jsonObject.getJSONArray("results");
						
						//if (resultArray.length()>0)
						String address = jsonObject.getString("display_name");
						if (address.length()>0) {
							//JSONObject subObject = resultArray.getJSONObject(0);
							//String address = subObject.getString("formatted_address");
							
							Message message = new Message();
							message.what = SHOW_LOCATION;
							message.obj = address;
							handler.sendMessage(message);
						}
					}
					 
					//经测试，GeoCoder 无法解析出地址
					// GeoCoder 要在主线程中创建
					//private Geocoder gc = new Geocoder(this, Locale.getDefault());
					/*String address = (gc.getFromLocation(
							location.getLatitude(), location.getLongitude(), 5)).toString();
					 
					Message message = new Message();
					message.what = SHOW_LOCATION;
					message.obj = address;
					handler.sendMessage(message);*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
