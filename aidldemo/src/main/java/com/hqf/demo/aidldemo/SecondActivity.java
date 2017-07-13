package com.hqf.demo.aidldemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hqf.demo.aidldemo.model.User;
import com.hqf.demo.aidldemo.util.MyConstants;
import com.hqf.demo.aidldemo.util.MyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by huangqianfang on 2017/7/13.
 * email huangqianfanghn@gmail.com
 */

public class SecondActivity extends Activity {

	private String TAG = "SecondActivity";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SecondActivity.this ,ThreeActivity.class));
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();
//		User user = (User) getIntent().getSerializableExtra("extra_user");
//		Log.d(TAG, "user:" + user.toString());

		recoverFromFile();
	}

	private void recoverFromFile() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				User user = null;
				File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
				if (cachedFile.exists()) {
					ObjectInputStream objectInputStream = null;
					try {
						objectInputStream = new ObjectInputStream(
								new FileInputStream(cachedFile));
						user = (User) objectInputStream.readObject();
						Log.d(TAG, "recover user:" + user);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} finally {
						MyUtils.close(objectInputStream);
					}
				}
			}
		}).start();
	}
}
