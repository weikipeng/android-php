package com.man.cfg;

/**
 * 游戏配置
 */
public class CFG {

	/**
	 * 屏幕宽度
	 */
	public static final int SCREEN_WIDTH = 320;
	
	/**
	 * 屏幕高度
	 */
	public static final int SCREEN_HEIGHT = 480;

	/**
	 * 可点击的边界值
	 * 用于女人角色（NetGameController）
	 */
	public static final float CLICK_MARGIN = 50;

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
	 * 游戏延迟
	 * 越小画面越细质
	 */
	public static final int DELAY_TIME = 50;
	
	/**
	 * 游戏级别
	 */
	public static final int[] GAME_LEVEL = {1,3,5,3,6,3,6,4,7,4,7,4,8,5,9,2,10,5,11};
}
