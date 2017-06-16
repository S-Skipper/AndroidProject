package com.dtt.webservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final int SUCCEESS = 1;
	public static final int FAIL = 2;
	
	private EditText drugName;
	private TextView resultText;
	private Button getButton;
	private String drug;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case SUCCEESS:
				resultText.setText(msg.obj.toString());
				break;

			default:
				resultText.setText("null");
				break;
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		drugName = (EditText) findViewById(R.id.drugName);
		resultText = (TextView) findViewById(R.id.result);
		getButton = (Button) findViewById(R.id.getResponse);
		
		getButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				drug = drugName.getText().toString().trim();
				drug = "乙醇";
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String resultString = getDrug(drug);
						Log.d("MainActivity", resultString);
						
						Message message = new Message();
						
						if (resultString=="null") {
							message.what=FAIL;
							handler.sendMessage(message);
						} else{
							message.what=SUCCEESS;
							message.obj = resultString;
							handler.sendMessage(message);
						}
					}
				}).start();
			}
		});
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public String getDrug(String drug_name){
		
		String response="";
		
		String namespace = "http://tempuri.org/";
		String endPoint = "http://211.66.136.32/WebService_Drug.asmx";
		String methodName = "GetDrug";
		
		String soapAction = namespace + methodName;

		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(namespace, methodName);

		// 设置需调用WebService接口需要传入的参数
		rpc.addProperty("ID", "1111");
		rpc.addProperty("PW", "1111");
		rpc.addProperty("infor", drug_name);
		// rpc.addProperty("Word", a);
		// rpc.addProperty("qqCode", a);
		// rpc.addProperty("b", b);

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Log.d("MainActivity", envelope.bodyIn.toString());
		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		// Log.d("MainActivity", object.getProperty(0).toString());
		// response = object.getProperty(0).toString();
		
		//Log.d("MainActivity", response);
		
		if (object==null) {
			response="null";
		}else {
			response = object.getProperty(0).toString();
		}
		
		Log.d("MainActivity", response);
		return response;
	}

}
