package com.app.special;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SpecialActivity extends Activity {

	private Button btnDemoCamera;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnDemoCamera = (Button) this.findViewById(R.id.btn_demo_camera);
		
	}
}