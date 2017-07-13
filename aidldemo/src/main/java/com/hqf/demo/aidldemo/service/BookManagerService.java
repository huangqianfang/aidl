package com.hqf.demo.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hqf.demo.aidldemo.aidl.Book;
import com.hqf.demo.aidldemo.aidl.IBookManager;
import com.hqf.demo.aidldemo.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by huangqianfang on 2017/7/13.
 * email huangqianfanghn@gmail.com
 */

public class BookManagerService extends Service {

	private static final String TAG = "BMS";

	private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
	private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();

	private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

	private Binder mBinder = new IBookManager.Stub() {
		@Override
		public List<Book> getBookList() throws RemoteException {
			return mBookList;
		}

		@Override
		public void addBook(Book book) throws RemoteException {
			mBookList.add(book);

		}

		@Override
		public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
			if(!mListenerList.contains(listener)){
				mListenerList.add(listener);
			}else{
				Log.d(TAG , "already exists.");
			}
			Log.d(TAG ,"registerListener , size:" +mListenerList.size());
		}

		@Override
		public void unRegisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
			if(mListenerList.contains(listener)){
				mListenerList.remove(listener);
				Log.d(TAG , "unregister listener succeed.");
			}else{
				Log.d(TAG , "not found, can not unregister");
			}

			Log.d(TAG ,"registerListener , size:" +mListenerList.size());
		}

		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mBookList.add(new Book(1, "Android"));
		mBookList.add(new Book(2, "Ios"));

		new Thread(new ServiceWorker()).start();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mIsServiceDestoryed.set(true);
	}

	private void onNewBookArrived(Book book) throws RemoteException {
		mBookList.add(book);
		Log.d(TAG ,"onNewBookArrived , notify listener:" +mListenerList.size());
		for(int i =0 ; i< mListenerList.size() ; i++){
			IOnNewBookArrivedListener listener = mListenerList.get(i);
			Log.d(TAG ,"onNewBookArrived , notify listener:" + listener);
			listener.onNewBookArrived(book);
		}
	}

	private class ServiceWorker implements Runnable{

		@Override
		public void run() {
			while (!mIsServiceDestoryed.get()){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int bookId = mBookList.size()+1;
				Book newBook =new Book(bookId , "new book #"+bookId);
				try {
					onNewBookArrived(newBook);
				} catch (RemoteException e) {
					e.printStackTrace();
				}


			}
		}
	}
}
