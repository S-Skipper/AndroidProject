package com.chemlab.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.chemlab.R;
import com.chemlab.util.HttpUtil;
import com.chemlab.util.MyApplication;

public class SplashActivity extends Activity {
	
	private static final int FAILURE = 0; // 失败
    private static final int SUCCESS = 1; // 成功
    private static final int OFFLINE = 2; // 如果支持离线阅读，进入离线模式
    private static final int SHOW_TIME_MIN = 1000;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

		setContentView(R.layout.activity_splash);
		
		new AsyncTask<Void, Void, Integer>() {
			 
            @Override
            protected Integer doInBackground(Void... params) {
            	int result;
                long startTime = System.currentTimeMillis();
                result = loadingCache();
                long loadingTime = System.currentTimeMillis() - startTime;
                if (loadingTime < SHOW_TIME_MIN) {
                    try {
                        Thread.sleep(SHOW_TIME_MIN - loadingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }
 
            @Override
            protected void onPostExecute(Integer result) {
            	if (result == SUCCESS) {
            		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
				} else if (result == FAILURE) {
					Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
				} else if (result == OFFLINE) {
					if (!(MyApplication.getString("user_id").equals("") 
							|| MyApplication.getString("user_password").equals("")
						)) {
						Intent intent = new Intent(SplashActivity.this, MainActivity.class);
	                    startActivity(intent);
					} else {
						Toast.makeText(SplashActivity.this, "请检查网络进行登录", Toast.LENGTH_LONG).show();
					}
				}
            	
            	finish();
                //两个参数分别表示进入的动画,退出的动画
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
 
            };
        }.execute(new Void[]{});
	}
	
	
 
    private int loadingCache() {
        if (!HttpUtil.isNetworkAvailable()) {
            return OFFLINE;
        }
        //return SUCCESS;
        
        String id = MyApplication.getString("user_id");
        /*String pw = MyApplication.getString("user_password");*/
        
        if (id.equals("")) {
        	return FAILURE;
		}
        
        return SUCCESS;
    }

}
