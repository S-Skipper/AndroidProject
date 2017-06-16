package com.dtt.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CompassAcyivity extends Activity {

	private SensorManager sensorManager;
	private ImageView compassImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compass_layout);
		
		compassImage = (ImageView) findViewById(R.id.compass_img);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
	}
	
	private SensorEventListener listener = new SensorEventListener() {
		
		float [] accelerometerValues = new float [3];
		float [] magneticValues = new float [3];
		private float lastRotateDegree;
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
				accelerometerValues = event.values.clone();
			} else if (event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) {
				magneticValues = event.values.clone();
			}
			
			float [] R = new float[9];
			float [] values = new float[3];
			SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
			SensorManager.getOrientation(R, values);
			
			float rotateDegree = -(float) Math.toDegrees(values[0]);
			if (Math.abs(rotateDegree-lastRotateDegree)>1) {
				RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree, 
								         Animation.RELATIVE_TO_SELF, 0.5f, 
								         Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setFillAfter(true);
				compassImage.startAnimation(animation);
				
				lastRotateDegree = rotateDegree;
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
