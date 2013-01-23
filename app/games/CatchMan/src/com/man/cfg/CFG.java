package com.man.cfg;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 游戏配置
 */
public class CFG {
	
	/**
	 * 屏幕宽度
	 */
	public static int SCREEN_WIDTH = 320;
	
	/**
	 * 屏幕高度
	 */
	public static int SCREEN_HEIGHT = 480;

	/**
	 * 屏幕偏移量
	 */
	public static float SCREEN_OFFSET_X = 0;
	
	/**
	 * 屏幕偏移量
	 */
	public static float SCREEN_OFFSET_Y = 0;
	
	/**
	 * 屏幕边界
	 */
	public static float SCREEN_LTX = 0;
	public static float SCREEN_LTY = 0;
	public static float SCREEN_RBX = SCREEN_WIDTH;
	public static float SCREEN_RBY = SCREEN_HEIGHT;
	
	/**
	 * 可点击的边界值 - 用于女人角色（NetGameController）
	 */
	public static float SCREEN_MARGIN = 50;

	/**
	 * 人物大小
	 */
	public static final float PEOPLE_RADIUS = 24;

	/**
	 * 女人速度
	 */
	public static final float WOMAN_SPEED = 10;

	/**
	 * 男人速度
	 */
	public static final float MAN_SPEED = 15;
	
	/**
	 * 游戏延迟（越小画面越细质）
	 */
	public static final int DELAY_TIME = 50;
	
	/**
	 * 游戏级别
	 */
	public static final int[] GAME_LEVEL = {1,3,5,3,6,3,6,4,7,4,7,4,8,5,9,2,10,5,11};
	
	/**
	 * 机型屏幕自适应（宽、高、边界）
	 */
	public static void autoScreenAdaption(Activity activity) {
		DisplayMetrics  dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float screenRealWidth = dm.widthPixels * dm.density;
		float screenRealHeight = dm.heightPixels * dm.density;
		SCREEN_OFFSET_X = (screenRealWidth - SCREEN_WIDTH) / 2;
		SCREEN_OFFSET_Y = (screenRealHeight - SCREEN_HEIGHT) / 2;
		SCREEN_LTX = SCREEN_LTX + SCREEN_OFFSET_X;
		SCREEN_LTY = SCREEN_LTY + SCREEN_OFFSET_Y;
		SCREEN_RBX = SCREEN_RBX + SCREEN_OFFSET_X;
		SCREEN_RBY = SCREEN_RBY + SCREEN_OFFSET_Y;
		Log.w("CFG", "SCREEN_WIDTH:" + screenRealWidth + ",SCREEN_HEIGHT:" + screenRealHeight);
		Log.w("CFG", "SCREEN_OFFSET_X:" + SCREEN_OFFSET_X + ",SCREEN_OFFSET_Y:" + SCREEN_OFFSET_Y);
	}
	
	public static float getRealX(float x) {
		return SCREEN_OFFSET_X + x;
	}
	
	public static float getRealY(float y) {
		return SCREEN_OFFSET_Y + y;
	}
}
