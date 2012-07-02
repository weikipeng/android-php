package com.app.demos.special;

import com.app.demos.special.R;
import com.app.demos.special.demo.DemoCamera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SpecialActivity extends Activity {

	private Button btnDemoCamera;
	private Button btnDemoSensor;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnDemoCamera = (Button) this.findViewById(R.id.btn_demo_camera);
		btnDemoCamera.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setClass(SpecialActivity.this, DemoCamera.class);
		        startActivity(intent);
			}
		});
		
		btnDemoSensor = (Button) this.findViewById(R.id.btn_demo_sensor);
	}
}