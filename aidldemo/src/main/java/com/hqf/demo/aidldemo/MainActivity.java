package com.hqf.demo.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hqf.demo.aidldemo.model.User;
import com.hqf.demo.aidldemo.service.MessengerService;
import com.hqf.demo.aidldemo.util.MyConstants;
import com.hqf.demo.aidldemo.util.MyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

	private String TAG = "MainActivity";
	private int MY_PERMISSION_REQUEST_CODE = 002;

	private Messenger mService;

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = new Messenger(service);
			Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
			Bundle bundle = new Bundle();
			bundle.putString("msg", "hello , this is a client .");
			msg.setData(bundle);
			msg.replyTo = mGetReplyMessager;
			try {
				mService.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MessengerService.class);
				bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mServiceConnection);
	}


	private Messenger mGetReplyMessager = new Messenger(new MessengerHandler());


	private static class MessengerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MyConstants.MSG_FROM_SERVICE:
					Log.d("ddd" , msg.getData().getString("msg"));
					break;
				default:
					super.handleMessage(msg);

					break;
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
//		boolean isAllGranted = checkPermissionAllGranted(
//				new String[] {
//						Manifest.permission.READ_EXTERNAL_STORAGE,
//						Manifest.permission.WRITE_EXTERNAL_STORAGE
//				}
//		);
//
//		if(isAllGranted){
//			persistToFile();
//		}else {
//
//			ActivityCompat.requestPermissions(
//					this,
//					new String[]{
//
//							Manifest.permission.READ_EXTERNAL_STORAGE,
//							Manifest.permission.WRITE_EXTERNAL_STORAGE
//					},
//					MY_PERMISSION_REQUEST_CODE
//			);
//		}
	}

	/**
	 * 检查是否拥有指定的所有权限
	 */
	private boolean checkPermissionAllGranted(String[] permissions) {
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
				// 只要有一个权限没有被授予, 则直接返回 false
				return false;
			}
		}
		return true;
	}


	private void persistToFile() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				User user = new User(1, "hello world", false);
				File dir = new File(MyConstants.CHAPTER_2_PATH);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
				ObjectOutputStream objectOutputStream = null;
				try {
					objectOutputStream = new ObjectOutputStream(
							new FileOutputStream(cachedFile));
					objectOutputStream.writeObject(user);
					Log.d(TAG, "persist user:" + user);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					MyUtils.close(objectOutputStream);
				}
			}
		}).start();
	}

	/**
	 * 第 3 步: 申请权限结果返回处理
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == MY_PERMISSION_REQUEST_CODE) {
			boolean isAllGranted = true;

			// 判断是否所有的权限都已经授予了
			for (int grant : grantResults) {
				if (grant != PackageManager.PERMISSION_GRANTED) {
					isAllGranted = false;
					break;
				}
			}

			if (isAllGranted) {
				// 如果所有的权限都授予了, 则执行备份代码
				persistToFile();

			} else {
				// 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮

			}
		}
	}

}
