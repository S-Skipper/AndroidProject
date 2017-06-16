package com.dtt;

import com.dtt.service.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AsyncTaskActivity extends Activity{
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		progressDialog = new ProgressDialog(AsyncTaskActivity.this);
		
		new DownLoadAsyncTask(AsyncTaskActivity.this,progressDialog).execute();
	}
	
	class DownLoadAsyncTask extends AsyncTask<Void, Integer, Boolean> {

		private Context context;
		private ProgressDialog progressDialog;
		
		public DownLoadAsyncTask(Context context,ProgressDialog progressDialog) {
			this.context = context;
			this.progressDialog = progressDialog;
		}

		@Override
		protected void onPreExecute() {
			//super.onPreExecute();
			progressDialog.setTitle("AsyncTask Test");
			progressDialog.setMessage("Download 0%");
			progressDialog.setCancelable(false);
			progressDialog.show();
			
			Log.d("Tag", "onPreExecute");
		}
		
		@Override
		protected Boolean doInBackground(Void... arg0) {
			Log.d("Tag", "doInBackground");
			try {
				int downloadPercent=0;
				while (true) {
					//int downloadPercent = donDownload();
					downloadPercent = donDownload(downloadPercent);
					//downloadPercent += 1;
					publishProgress(downloadPercent);
					if (downloadPercent >= 100) {
						break;
					}
					Log.d("Tag", downloadPercent+"");
				}
				
			} catch (Exception e) {
				return false;
			}
			
			return true;
		}

		private int donDownload(int percent) {
			int result = percent;
			result += 1;
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			Log.d("Tag", "onProgressUpdate");
			//super.onProgressUpdate(values);
			progressDialog.setMessage("Download "+values[0]+"%");
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			Log.d("Tag", "onPostExecute");
			//super.onPostExecute(result);
			progressDialog.dismiss();
			
			if (result) {
				Toast.makeText(context, "Download Succeeded!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, "Download Failed!", Toast.LENGTH_LONG).show();
			}
		}

	}

}

