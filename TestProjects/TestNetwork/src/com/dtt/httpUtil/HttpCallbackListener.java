package com.dtt.httpUtil;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);
}
