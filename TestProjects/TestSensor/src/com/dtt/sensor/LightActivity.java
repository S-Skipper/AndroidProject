package com.dtt.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class LightActivity extends Activity {

	private SensorManager sensorManager;
	private TextView lightLevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		lightLevel = (TextView) findViewById(R.id.light_level);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		
		sensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sensorManager!=null) {
			sensorManager.unregisterListener(listener);
		}
	}
	
	private SensorEventListener listener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float value = event.values[0];
			lightLevel.setText("Current light level is : "+value+" lx");
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
}
