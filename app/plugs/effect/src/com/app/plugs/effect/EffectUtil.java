package com.app.plugs.effect;

import com.app.plugs.effect.view.EffectImageView;
import com.app.plugs.effect.view.EffectTextView;

import android.content.Context;

public class EffectUtil {

	public static EffectTextView buildTextView(Context context, int ani, int size, String[] texts) {
		EffectTextView etv = new EffectTextView(context);
		etv.setAnimation(ani);
		etv.setTextSize(size);
		etv.setTexts(texts);
		return etv;
	}
	
	public static EffectImageView buildImageView(Context context, int ani, int[] resIds) {
		EffectImageView eiv = new EffectImageView(context);
		eiv.setAnimation(ani);
		eiv.setResources(resIds);
		return eiv;	
	}
	
	
}