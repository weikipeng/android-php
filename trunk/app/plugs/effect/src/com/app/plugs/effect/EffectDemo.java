package com.app.plugs.effect;

import com.app.plugs.effect.view.EffectTextView;
import com.app.plugs.effect.view.EffectView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.app.Activity;

public class EffectDemo extends Activity implements OnItemSelectedListener {
	
	private LinearLayout container;
	private EffectView mView;
	private String[] mTexts = {"今日新闻1", "今日新闻2", "今日新闻3"};
	private int[] mImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3};
	
	private Spinner sp1;
	private ArrayAdapter<String> aa1;
	private String[] menu1 = {"文字", "图片"};
	
	private Spinner sp2;
	private ArrayAdapter<String> aa2;
	private String[] menu2 = {"向上", "向下", "向左", "向右", "旋转", "大小", "渐变"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		container = (LinearLayout) this.findViewById(R.id.container);
		
		sp1 = (Spinner) findViewById(R.id.spinner1);
		aa1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, menu1);
		aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(aa1);
		sp1.setOnItemSelectedListener(this);
		
		sp2 = (Spinner) findViewById(R.id.spinner2);
		aa2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, menu2);
		aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(aa2);
		sp2.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getAdapter() == aa1) {
			container.removeAllViews();
			switch (position) {
				case 0:
					mView = EffectUtil.buildTextView(this, EffectTextView.ANI_UP, 30, mTexts);
					container.addView(mView);
					mView.start();
					break;
				default:
					mView = EffectUtil.buildImageView(this, EffectView.ANI_UP, mImages);
					container.addView(mView);
					mView.start();
					break;
			}
		}
		if (parent.getAdapter() == aa2) {
			switch (position) {
				case 0:
					mView.setAnimation(EffectView.ANI_UP);
					break;
				case 1:
					mView.setAnimation(EffectView.ANI_DOWN);
					break;
				case 2:
					mView.setAnimation(EffectView.ANI_LEFT);
					break;
				case 3:
					mView.setAnimation(EffectView.ANI_RIGHT);
					break;
				case 4:
					mView.setAnimation(EffectView.ANI_ROTATE);
					break;
				case 5:
					mView.setAnimation(EffectView.ANI_SCALE);
					break;
				default:
					mView.setAnimation(EffectView.ANI_FADE);
					break;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
