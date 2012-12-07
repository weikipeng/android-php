package com.app.plugs.effect.view;

import com.app.plugs.effect.anim.EffectAnimations;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class EffectView extends ViewFlipper {

	public static int ANI_UP = 0;
	public static int ANI_DOWN = 1;
	public static int ANI_LEFT = 2;
	public static int ANI_RIGHT = 3;
	public static int ANI_ROTATE = 4;
	public static int ANI_SCALE = 5;
	public static int ANI_FADE = 6;
	
	public EffectView(Context context) {
		super(context);
	}
	
	public EffectView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setAnimation(int ani) {
		switch (ani) {
			case 0:
				this.setInAnimation(EffectAnimations.getUpIn());
				this.setOutAnimation(EffectAnimations.getUpOut());
				break;
			case 1:
				this.setInAnimation(EffectAnimations.getDownIn());
				this.setOutAnimation(EffectAnimations.getDownOut());
				break;
			case 2:
				this.setInAnimation(EffectAnimations.getLeftIn());
				this.setOutAnimation(EffectAnimations.getLeftOut());
				break;
			case 3:
				this.setInAnimation(EffectAnimations.getRightIn());
				this.setOutAnimation(EffectAnimations.getRightOut());
				break;
			case 4:
				this.setInAnimation(EffectAnimations.getRotateIn());
				this.setOutAnimation(EffectAnimations.getRotateOut());
				break;
			case 5:
				this.setInAnimation(EffectAnimations.getScaleIn());
				this.setOutAnimation(EffectAnimations.getScaleOut());
				break;
			default:
				this.setInAnimation(EffectAnimations.getFadeIn());
				this.setOutAnimation(EffectAnimations.getFadeOut());
				break;
		}
	}
	
	public void setInterval(int ms) {
		this.setFlipInterval(ms);
	}
	
	public void start() {
		this.startFlipping();
	}
}