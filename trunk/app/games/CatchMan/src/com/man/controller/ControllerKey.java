package com.man.controller;

import android.app.Activity;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * ¼üÅÌ¿ØÖÆÆ÷
 */
public class ControllerKey extends Controller implements OnTouchListener {

	public ControllerKey(Activity activity) {
		super(activity);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (live) {
			float x = event.getX();
			float y = event.getY();
			PointF direction = new PointF(x, y);
			man.forwardTo(direction);
//			man.move();
		}
		return false;
	}
	
}
