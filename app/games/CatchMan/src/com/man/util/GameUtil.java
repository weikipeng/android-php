package com.man.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public final class GameUtil {
	
	public static void setHighScore(Activity activity, int score) {
		SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("highScore", "" + score);
		editor.commit();
	}
	
	public static int getHighScore(Activity activity) {
		SharedPreferences settings = activity.getPreferences(Context.MODE_PRIVATE);
		String highScore = settings.getString("highScore", "0");
		return Integer.parseInt(highScore);
	}
	
	public static void forward(Activity activity, Class<?> target) {
		Intent intent = new Intent();
		intent.setClass(activity, target);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
		// 关闭切换动画效果
		activity.overridePendingTransition(0, 0);
		activity.finish();
	}
	
	public static void forward (Activity activity, Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(activity, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		activity.startActivity(intent);
		// 关闭切换动画效果
		activity.overridePendingTransition(0, 0);
		activity.finish();
	}
}