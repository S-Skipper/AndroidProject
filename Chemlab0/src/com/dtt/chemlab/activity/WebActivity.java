package com.dtt.chemlab.activity;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.dtt.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WebActivity extends Activity {
	private EditText aText;
	private EditText bText;
	private TextView resultView;
	private Button queryButton;
	private String a, b, result;
	
	private Handler handle = new Handler() {

		public void handleMessage(Message msg) {
			String result = msg.obj.toString();
			if ("Y".equalsIgnoreCase(result)) {
				result = "在线";
			} else {
				result = "离线";
			}
			resultView.setText(result);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		aText = (EditText) findViewById(R.id.args_a);
		bText = (EditText) findViewById(R.id.args_b);
		resultView = (TextView) findViewById(R.id.result_text);
		queryButton = (Button) findViewById(R.id.query_btn);

		aText.setHint("输入QQ号查询在线状态");
		queryButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 手机号码（段）
				a = aText.getText().toString().trim();
				b = bText.getText().toString().trim();
				// 简单判断用户输入的手机号码（段）是否合法
				/*if ("".equals(a) || "".equals(b) ) {
					resultView.setText("输入有误");
					return;
				}*/
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						//Log.d("MainActivity", "in thread");
						getRemoteInfo(a,b);
						Message msg = handle.obtainMessage();
						msg.obj = result;
						handle.sendMessage(msg);
					}
				}).start();

			}
		});
	}

	public void getRemoteInfo(String a,String b) {
		// 命名空间
		String nameSpace = "http://WebXml.com.cn/";
		// 调用的方法名称
		String methodName = "qqCheckOnline";
		// EndPoint
		String endPoint = "http://www.webxml.com.cn/webservices/qqOnlineWebService.asmx";
		// SOAP Action
		String soapAction = "http://WebXml.com.cn/qqCheckOnline";

		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId
		//rpc.addProperty("Word", a);
		rpc.addProperty("qqCode", a);
		//rpc.addProperty("b", b);

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

		Log.d("MainActivity", envelope.bodyIn.toString());
		// 获取返回的数据
		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		Log.d("MainActivity", object.getProperty(0).toString());
		result = object.getProperty(0).toString();
	}
}