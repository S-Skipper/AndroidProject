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

public class MainActivity extends Activity {

	private SensorManager sensorManager;
	private TextView sensorValueText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sensorValueText = (TextView) findViewById(R.id.light_level);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		Sensor sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(listener, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private SensorEventListener listener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			
			if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
				float xValue = Math.abs(event.values[0]);
				float yValue = Math.abs(event.values[1]);
				float zValue = Math.abs(event.values[2]);
				
				if(xValue>15||yValue>15||zValue>15){
					Toast.makeText(MainActivity.this, "ҡһҡ", Toast.LENGTH_SHORT).show();
				}
				
				/*sensorValueText.setText("Value in direction X is :"+xValue+"\n"
	               +"Value in direction Y is :"+yValue+"\n"
	               +"Value in direction Z is :"+zValue+"\n");*/
			}
			
			if (event.sensor.getType()==Sensor.TYPE_LIGHT) {
				float value = event.values[0];
				sensorValueText.setText("Current light level is : "+value+" lx");
			}
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
