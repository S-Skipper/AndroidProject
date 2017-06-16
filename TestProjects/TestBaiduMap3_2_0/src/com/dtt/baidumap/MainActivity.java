package com.dtt.baidumap;

import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity {

	private MapView mapView;
	
	private BaiduMap baiduMap;
	private LocationManager locationManager;
	private String provider;
	
	private boolean isFirstLocate = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		
		mapView = (MapView) findViewById(R.id.map_view);
		baiduMap = mapView.getMap();
		baiduMap.setMyLocationEnabled(true);
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			Toast.makeText(this, "No provides Available!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Location location = locationManager.getLastKnownLocation(provider);
		if (location!=null) {
			navigateTo(location);
		}
		locationManager.requestLocationUpdates(provider, 5000, 10, locationListener);
	}
	
	private void navigateTo(Location location) {
		if (isFirstLocate) {
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
			MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
			baiduMap.animateMapStatus(update);
			update = MapStatusUpdateFactory.zoomTo(16);
			baiduMap.animateMapStatus(update);
			isFirstLocate=false;
		}
		
		MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
		locationBuilder.latitude(location.getLatitude());
		locationBuilder.longitude(location.getLongitude());
		
		MyLocationData locationData = locationBuilder.build();
		baiduMap.setMyLocationData(locationData);
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
			if (location!=null) {
				navigateTo(location);
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		baiduMap.setMyLocationEnabled(false);
		mapView.onDestroy();
		if (locationListener!=null) {
			locationManager.removeUpdates(locationListener);
		}
	}
}
