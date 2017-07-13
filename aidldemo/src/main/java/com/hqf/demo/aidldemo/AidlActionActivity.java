package com.hqf.demo.aidldemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.hqf.demo.aidldemo.aidl.Book;
import com.hqf.demo.aidldemo.aidl.IBookManager;
import com.hqf.demo.aidldemo.aidl.IOnNewBookArrivedListener;
import com.hqf.demo.aidldemo.service.BookManagerService;

import java.util.List;

/**
 * Created by huangqianfang on 2017/7/13.
 * email huangqianfanghn@gmail.com
 */

public class AidlActionActivity extends Activity {

	private static final String TAG = "AidlActionActivity";

	private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

	private IBookManager mRemoteBookManager;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_NEW_BOOK_ARRIVED:
					Log.d(TAG, "receiver new book :" + msg.obj);
					break;
				default:
					super.handleMessage(msg);
					break;
			}
		}
	};

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			IBookManager iBookManager = IBookManager.Stub.asInterface(service);

			mRemoteBookManager = iBookManager;
			try {

				List<Book> list = iBookManager.getBookList();
				Log.d(TAG, "查找 book list  list tpye ：" + list.getClass().getCanonicalName());

				Log.d(TAG, "查找 book" +
						" list:" + list.toString());
				Book newBook = new Book(3 , "Android 进阶");
				iBookManager.addBook(newBook);
				Log.d(TAG , "add book:" + newBook);
				List<Book> newList  = iBookManager.getBookList();
				Log.d(TAG , "查找 book list：" + newList.toString());
				iBookManager.registerListener(mIOnNewBookArrivedLister);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	private IOnNewBookArrivedListener mIOnNewBookArrivedLister = new IOnNewBookArrivedListener.Stub() {
		@Override
		public void onNewBookArrived(Book newBook) throws RemoteException {
			mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED , newBook).sendToTarget();

		}

		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aidl);
		findViewById(R.id.aidl_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AidlActionActivity.this, BookManagerService.class);
				bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

			}
		});
	}

	@Override
	protected void onDestroy() {
		if(mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()){
			Log.d(TAG , "unrigister listener:" + mIOnNewBookArrivedLister);
			try {
				mRemoteBookManager.unRegisterListener(mIOnNewBookArrivedLister);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		unbindService(serviceConnection);
		super.onDestroy();
	}
}
