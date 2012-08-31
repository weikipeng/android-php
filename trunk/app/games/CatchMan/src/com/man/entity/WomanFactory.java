package com.man.entity;

import com.man.cfg.CFG;

import android.graphics.PointF;

/**
 * 女人工厂
 */
public class WomanFactory {

	/**
	 * 生产一个女人
	 */
	public static Woman getWoman(){
		
		//生产一个丑女人
		Woman woman = new UglyWoman();
		
		//设置女人属性
		float rnd1 = (float) Math.random();
		float rnd2 = (float) Math.random();
		float rnd3 = (float) Math.random();
		PointF point = new PointF();
		if (rnd1 > 0.5){
			//左右两边
			point.x = CFG.SCREEN_WIDTH * rnd3;
			point.y = (rnd2 > 0.5)? 0 : CFG.SCREEN_HEIGHT;
		} else {
			//上下两边
			point.y = CFG.SCREEN_HEIGHT * rnd3;
			point.x = (rnd2 > 0.5) ? 0 : CFG.SCREEN_WIDTH;
		}
		woman.setLocation(point);
		
		//返回女人
		return woman;
	}
}
