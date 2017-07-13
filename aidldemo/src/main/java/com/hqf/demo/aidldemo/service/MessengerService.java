package com.hqf.demo.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hqf.demo.aidldemo.util.MyConstants;

/**
 * Created by huangqianfang on 2017/7/13.
 * email huangqianfanghn@gmail.com
 */

public class MessengerService extends Service {

	private static final String TAG = "MessengerService";

	private static class  MessengerHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case MyConstants.MSG_FROM_CLIENT:
					Log.d("ddd" , msg.getData().getString("msg"));
					Messenger client = msg.replyTo;
					Message replyMessage = Message.obtain(null , MyConstants.MSG_FROM_SERVICE);
					Bundle bundle = new Bundle();
					bundle.putString("msg" , "我收到你的信息了 小伙子");
					replyMessage.setData(bundle);
					try {
						client.send(replyMessage);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					break;
				default:
					super.handleMessage(msg);

			}
		}
	}

	private  final Messenger mMessenger = new Messenger(new MessengerHandler());

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}
}
