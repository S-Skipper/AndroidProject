package com.dtt.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AccelerometerActivity extends Activity {

	private SensorManager sensorManager;
	private TextView sensorValueText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sensorValueText = (TextView) findViewById(R.id.light_level);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(listener, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private SensorEventListener listener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			float xValue = Math.abs(event.values[0]);
			float yValue = Math.abs(event.values[1]);
			float zValue = Math.abs(event.values[2]);
			
			if(xValue>15||yValue>15||zValue>15){
				Toast.makeText(AccelerometerActivity.this, "ҡһҡ", Toast.LENGTH_SHORT).show();
			}
			
			sensorValueText.setText("Value in direction X is :"+xValue+"\n"
					               +"Value in direction Y is :"+yValue+"\n"
					               +"Value in direction Z is :"+zValue+"\n");
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (sensorManager!=null) {
			sensorManager.unregisterListener(listener);
		}
	}
	
}
