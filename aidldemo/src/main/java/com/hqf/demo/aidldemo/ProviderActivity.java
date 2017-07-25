package com.hqf.demo.aidldemo;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by huangqianfang on 2017/7/14.
 * email huangqianfanghn@gmail.com
 */

public class ProviderActivity extends Activity {

	AsyncTask asyncTask = null;

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_provider);
		Uri uri = Uri.parse("content://com.hqf.demo.aidldemo.provider");
		getContentResolver().query(uri , null , null, null , null);
		getContentResolver().query(uri , null , null, null , null);
		getContentResolver().query(uri , null , null, null , null);

		handler.post(new Runnable() {
			@Override
			public void run() {

			}
		});

	}
}
