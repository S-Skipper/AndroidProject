package com.dtt.testgl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;

public class MainActivity1 extends Activity {

	private GLSurfaceView glView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//MyView myView = new MyView(this);
		//setContentView(myView);
		
		glView= new MyGLSurfaceView(this);
		setContentView(glView);
	}
	
	class MyGLSurfaceView extends GLSurfaceView{

		public MyGLSurfaceView(Context context) {
			super(context);

			try {
				// Create an OpenGL ES 2.0 context
				setEGLContextClientVersion(2);
				
				// Set the Renderer for drawing on the GLSurfaceView
				setRenderer(new MyRender());
				
				// Render the view only when there is a change in the drawing data
				setRenderMode(RENDERMODE_WHEN_DIRTY);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public class MyRender implements Renderer{

		@Override
		public void onDrawFrame(GL10 unused) {
			// Redraw background color
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		}

		@Override
		public void onSurfaceChanged(GL10 unused, int width, int height) {
			GLES20.glViewport(0, 0, width, height);
		}

		@Override
		public void onSurfaceCreated(GL10 unused, EGLConfig config) {
			// Set the background frame color
			GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		}
		
	}

}
