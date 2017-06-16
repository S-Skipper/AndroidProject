package com.dtt.testgl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GLSurfaceView glView = new GLSurfaceView(this);
        MyRenderer renderer = new MyRenderer();
        glView.setRenderer(renderer);
        setContentView(glView);
	}

}

class MyRenderer implements Renderer {
    float[] mTriangleData = new float[] {
            0.1f, 0.6f, 0.0f,
            -0.3f, 0.0f, 0.0f,
            0.3f, 0.1f, 0.0f
    };
    int[] mTriangleColor = new int[] {
            65535, 0, 0, 0, 
            0, 65535, 0, 0,
            0, 0, 65535, 0,
    };
     
    float[] mRectData = new float[] {
            0.4f, 0.4f, 0.0f,
            0.4f, -0.4f, 0.0f,
            -0.4f, 0.4f, 0.0f, 
            -0.4f, -0.4f, 0.0f
    };
     
    int[] mRectColor = new int[] {
            0, 65535, 0, 0, 
            0, 0, 65535, 0, 
            65535, 0, 0, 0,
            65535, 65535, 0, 0,
    };
     
    FloatBuffer mTriangleDataBuffer;
    IntBuffer mTriangleColorBuffer;
     
    FloatBuffer mRectDataBuffer;
    IntBuffer mRectColorBuffer;
     
    public MyRenderer() {
        mTriangleDataBuffer= bufferUtil(mTriangleData);
        mTriangleColorBuffer = bufferUtil(mTriangleColor);
         
        mRectDataBuffer = bufferUtil(mRectData);
        mRectColorBuffer = bufferUtil(mRectColor);
    }
     
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
         
        gl.glLoadIdentity();
        gl.glTranslatef(-0.6f, 0.0f, -1.5f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleDataBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, mTriangleColorBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
         
        gl.glLoadIdentity();
        gl.glTranslatef(0.6f, 0.8f, -1.5f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mRectDataBuffer);
        gl.glColorPointer(4, GL10.GL_FIXED, 0, mRectColorBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
         
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
 
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float) width / height;
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }
 
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0, 0, 0, 0);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }
 
    public IntBuffer bufferUtil(int []arr){  
        IntBuffer buffer;
 
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
 
        buffer = qbb.asIntBuffer();
        buffer.put(arr);
        buffer.position(0);
 
        return buffer;
   }
     
    public FloatBuffer bufferUtil(float []arr){  
        FloatBuffer buffer;
 
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        qbb.order(ByteOrder.nativeOrder());
 
        buffer = qbb.asFloatBuffer();
        buffer.put(arr);
        buffer.position(0);
 
        return buffer;
   }
}
