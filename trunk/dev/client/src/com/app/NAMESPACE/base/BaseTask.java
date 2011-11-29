package com.app.NAMESPACE.base;

public class BaseTask {

	public static final int TASK_COMPLETE = 0;
	public static final int SHOW_TOAST = 1;
	public static final int SHOW_LOADBAR = 2;
	public static final int HIDE_LOADBAR = 3;
	
	private int id = 0;
	private String name = "";
	private BaseHandler handler;
	
	public BaseTask() {
		handler = new BaseHandler();
	}
	
	public BaseTask(BaseHandler bh) {
		this.handler = bh;
	}
	
	public int getId () {
		return this.id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public BaseHandler getHandler () {
		return this.handler;
	}
	
	public void setHandler (BaseHandler bh) {
		this.handler = bh;
	}
	
	public void onStart () {
//		Log.w("BaseTask", "onStart");
	}
	
	public void onComplete () {
//		Log.w("BaseTask", "onComplete");
	}
	
	public void onComplete (String httpResult) {
//		Log.w("BaseTask", "onComplete");
	}
	
	public void onError (String error) {
//		Log.w("BaseTask", "onError");
	}
	
	public void onStop () throws Exception {
//		Log.w("BaseTask", "onStop");
	}
	
}