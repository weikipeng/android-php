package com.app.NAMESPACE.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class BaseHandler extends Handler {
	
	public BaseHandler () {
		
	}
	
	public BaseHandler (Looper i) {
		super(i);
	}
	
	@Override
	public void handleMessage(Message msg) {
		Log.w("BaseHandler", "msg:" + msg.getData().toString());
	}
	
}