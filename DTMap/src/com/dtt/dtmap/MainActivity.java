package com.dtt.dtmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.dtt.dtmap.ShakeListener.OnShakeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * 演示MapView的基本用法
 */
public class MainActivity extends Activity {
	// 定位相关
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public boolean isFristLocation = true;

	MapView mMapView = null;
	TextView tv = null;
	BaiduMap mBaiduMap;

	// pedometer
	ShakeListener mShakeListener = null;
	Vibrator mVibrator;

	private float mCurrentLatitude;
	private float mCurrentLongitude;
//	private float mLastLatitude;
//	private float mLastLongitude;
	private boolean isDraw = false;
	private boolean isStep = false;
	List<LatLng> points = new ArrayList<LatLng>();

	private int mStep = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		tv = (TextView) findViewById(R.id.tv);

		mBaiduMap = mMapView.getMap();
		// 放大16倍
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
		// 普通地图
		// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		/*
		 * LatLng center = new LatLng(22.352921, 113.596621); //定义地图状态 MapStatus
		 * mMapStatus = new MapStatus.Builder() .target(center) .zoom(16)
		 * .build(); //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		 * 
		 * MapStatusUpdate mMapStatusUpdate =
		 * MapStatusUpdateFactory.newMapStatus(mMapStatus); //改变地图状态
		 * mBaiduMap.setMapStatus(mMapStatusUpdate);
		 */
		// mBaiduMap.addOverlay(new
		// MarkerOptions().position(center).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_geo)));

		/*
		 * LatLng center = new LatLng(22.352921, 113.596621); mMapView = new
		 * MapView(this, new BaiduMapOptions().mapStatus(new
		 * MapStatus.Builder().target(center).build()));
		 * mBaiduMap.addOverlay(new
		 * MarkerOptions().position(center).icon(BitmapDescriptorFactory
		 * .fromResource(R.drawable.icon_geo))); setContentView(mMapView);
		 */
		isFristLocation = true;
		initLocation();
		// start location
		// mLocationClient.start();

		// ---------------pedometer------------------
		// drawerSet ();//设置 drawer监听 切换 按钮的方向
		// 获得振动器服务
		mVibrator = (Vibrator) getApplication().getSystemService(
				VIBRATOR_SERVICE);
		// 实例化加速度传感器检测类
		mShakeListener = new ShakeListener(MainActivity.this);

		mShakeListener.setOnShakeListener(new OnShakeListener() {
			public void onShake() {
				if(isStep){
					mShakeListener.stop();
					mStep++;
					tv.setText("Step: "+mStep);
					mShakeListener.start();
				}
				
				/*
				mShakeListener.stop();
				mStep++;
				tv.setText("Step: "+mStep);
				//startVibrato(); // 开始 震动
				
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, mStep + "摇啊摇啊摇啊摇",
								Toast.LENGTH_SHORT).show();

						mVibrator.cancel();
						mShakeListener.start();
					}
				}, 1000);
				mShakeListener.start();
				*/
			}
		});
		// ----------------------------------------------------//
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);

		menu.add(0, 0, 0, "记录轨迹");
		menu.add(0, 1, 0, "开始记步");
		menu.add(0, 2, 0, "保存轨迹");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			if(isDraw){
				isDraw = false;
				Toast.makeText(MainActivity.this, "绘制轨迹结束", Toast.LENGTH_SHORT).show();
				//drawPolyline();
				item.setTitle("记录轨迹");
			}
			else{
				isDraw = true;
				Toast.makeText(MainActivity.this, "绘制轨迹开始", Toast.LENGTH_SHORT).show();
				//drawPolyline();
				item.setTitle("停止绘制");
			}
			break;
		case 1:
			if(isStep){
				isStep = false;
				tv.setText("Step: "+mStep);
				item.setTitle("开始记步");
				Toast.makeText(MainActivity.this, "记步停止", Toast.LENGTH_SHORT).show();
			}
			else{
				isStep = true;
				mStep = 0;
				tv.setText("Step: "+mStep);
				item.setTitle("停止记步");
				Toast.makeText(MainActivity.this, "记步开始", Toast.LENGTH_SHORT).show();
			}
			break;
		case 2:
			saveState();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void saveState(){
		try {
			//判断SDcard是否存在并且可读写
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				saveToSdCard("TestRoad.txt");
			}else{
				Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
			}
		} catch (IOException e) {
			Toast.makeText(MainActivity.this, "write error", Toast.LENGTH_SHORT).show();
			//e.printStackTrace();
		}
		Toast.makeText(MainActivity.this, "轨迹已经保存", Toast.LENGTH_SHORT).show();
	}
	
	//保存文件到SD卡
	public void saveToSdCard(String filename) throws IOException 
	{
		//得到手机默认存储目录。并实例化
		//File externDir = Environment.getExternalStorageDirectory();
		File file=new File(Environment.getExternalStorageDirectory(),filename);
		FileOutputStream fos=new FileOutputStream(file);
		
		int plen = 0;
		while(plen <points.size()){
			String content = points.get(plen).toString();
		    fos.write(("<"+plen+">: "+content+"\n").getBytes());
		    plen++;
		}
		
		fos.close();
	}

	private void initLocation() {
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		// option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 5000; // 每5秒获取一次位置信息
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		// option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		// option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		// option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		// option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		// option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		// option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		// option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;

			mCurrentLatitude = (float) location.getLatitude();
			mCurrentLongitude = (float) location.getLongitude();

			// 构造定位数据
			MyLocationData locData = new MyLocationData.Builder()
					.latitude(mCurrentLatitude).longitude(mCurrentLongitude)
					.build();

			// 设置定位数据
			mBaiduMap.setMyLocationData(locData);

			// 设置自定义图标
			BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_geo);
			MyLocationConfiguration config = new MyLocationConfiguration(
					LocationMode.NORMAL, true, mCurrentMarker);
			mBaiduMap.setMyLocationConfigeration(config);

			// 第一次定位时，将地图位置移动到当前位置
			if (isFristLocation) {
				isFristLocation = false;
				LatLng ll = new LatLng(mCurrentLatitude, mCurrentLongitude);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);

//				mLastLatitude = mCurrentLatitude;
//				mLastLongitude = mCurrentLongitude;
				
				points.add(ll);
			}
			
			if(isDraw){
				addPoint();
			}
			
//			if(isDraw&&!points.isEmpty()){
//				drawPolyline();
//			}
		}

		public void addPoint() {
			LatLng pt = new LatLng(mCurrentLatitude, mCurrentLongitude);
			points.add(pt);
			
			if(!points.isEmpty()){
				drawPolyline();
			}
			
			/*
			float deltaLat = mCurrentLatitude - mLastLatitude;
			float deltaLon = mCurrentLongitude - mLastLongitude;
			int distance = (int) Math.sqrt(deltaLat * deltaLat + deltaLon
					* deltaLon);
            
			if (true||distance > 10) {
				LatLng pt = new LatLng(mCurrentLatitude, mCurrentLongitude);
				points.add(pt);

				mLastLatitude = mCurrentLatitude;
				mLastLongitude = mCurrentLongitude;
			}
			*/
		}
		
		public void drawPolyline(){			
			// 清除所有图层
			//mMapView.getMap().clear();

			OverlayOptions ooPolyline = new PolylineOptions().width(6)
					.color(0xAAFF0000).points(points);
			mBaiduMap.addOverlay(ooPolyline);
		}
	}

	@Override
	protected void onStart() {
		// 开启图层定位
		mBaiduMap.setMyLocationEnabled(true);
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		}
		// 开启方向传感器
		// myOrientationListener.start();
		super.onStart();
	}

	@Override
	protected void onStop() {
		// 关闭图层定位
		mBaiduMap.setMyLocationEnabled(false);
		mLocationClient.stop();

		// 关闭方向传感器
		// myOrientationListener.stop();
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	// 定义震动
	public void startVibrato() {
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);
		// 第一个｛｝里面是节奏数组，
		// 第二个参数是重复次数，-1为不重复，非-1则从pattern的指定下标开始重复
	}

}
