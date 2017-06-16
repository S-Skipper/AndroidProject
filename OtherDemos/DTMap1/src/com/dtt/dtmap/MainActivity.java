package com.dtt.dtmap;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;

import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * 演示MapView的基本用法
 */
public class MainActivity extends Activity {
	MapView mMapView = null;
	BaiduMap mBaiduMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext
		//注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		//获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		
	  	mBaiduMap = mMapView.getMap();
		//普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		
		LatLng center = new LatLng(22.352921, 113.596621);
		mMapView = new MapView(this, new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(center).build()));
		setContentView(mMapView);
		
		
/*		
        //定义多边形的五个顶点
		LatLng pt1 = new LatLng(39.93923, 116.357428);
		LatLng pt2 = new LatLng(39.91923, 116.327428);
		LatLng pt3 = new LatLng(39.89923, 116.347428);
		LatLng pt4 = new LatLng(39.89923, 116.367428);
		LatLng pt5 = new LatLng(39.91923, 116.387428);
		List<LatLng> pts = new ArrayList<LatLng>();
		pts.add(pt1);
		pts.add(pt2);
		pts.add(pt3);
		pts.add(pt4);
		pts.add(pt5);
		//构建用户绘制多边形的Option对象
		OverlayOptions polylineOption = new PolylineOptions()
		.width(5)
		.color(0xAAFF0000)
		.points(pts);
		
		//在地图上添加多边形Option，用于显示
		mBaiduMap.addOverlay(polylineOption);
*/
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
	}

}

