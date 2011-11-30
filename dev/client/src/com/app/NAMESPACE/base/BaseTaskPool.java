package com.app.NAMESPACE.base;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.app.NAMESPACE.util.AppClient;

public class BaseTaskPool {
	
	static private ExecutorService taskPool;
	
	public BaseTaskPool () {
		taskPool = Executors.newCachedThreadPool();
	}
	
	public BaseTaskPool (int size) {
		taskPool = Executors.newFixedThreadPool(size);
	}
	
	// http post task with params
	public void addTask (int taskId, String taskUrl, HashMap<String, String> taskArg, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(taskUrl, taskArg, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}
	
	// http post task without params
	public void addTask (int taskId, String taskUrl, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(taskUrl, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}
	
	// custom task
	public void addTask (int taskId, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(null, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}
	
	public static class TaskThread implements Runnable {
		private String taskUrl;
		private HashMap<String, String> taskArg;
		private BaseTask baseTask;
		private int delayTime = 0;
		public TaskThread(String taskUrl, HashMap<String, String> taskArg, BaseTask baseTask, int delayTime) {
			this.taskUrl = taskUrl;
			this.taskArg = taskArg;
			this.baseTask = baseTask;
			this.delayTime = delayTime;
		}
		@Override
		public void run() {
			try {
//				Looper.prepare();
				baseTask.onStart();
				String httpResult = null;
				// set timeout
				if (this.delayTime > 0) {
					Thread.sleep(this.delayTime);
				}
				try {
					// remote task
					if (this.taskUrl != null) {
						// http get
						if (taskArg == null) {
							AppClient client = new AppClient(this.taskUrl);
							httpResult = client.get();
						// http post
						} else {
							AppClient client = new AppClient(this.taskUrl);
							httpResult = client.post(this.taskArg);
						}
					}
					// remote task
					if (httpResult != null) {
						baseTask.onComplete(httpResult);
					// local task
					} else {
						baseTask.onComplete();
					}
				} catch (Exception e) {
					baseTask.onError(e.getMessage());
				}
				baseTask.onStop();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
//				Looper.loop();
			}
		}
	}
	
}