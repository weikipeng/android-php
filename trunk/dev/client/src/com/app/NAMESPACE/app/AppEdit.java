package com.app.NAMESPACE.app;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.auth.AuthApp;
import com.app.NAMESPACE.base.BaseMessage;
import com.app.NAMESPACE.base.C;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class AppEdit extends AuthApp {
	
	private EditText mEditText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app_edit);
		// show keyboard
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		Bundle params = this.getIntent().getExtras();
		final int action = params.getInt("action");
		final String value = params.getString("value");
		
		mEditText = (EditText) this.findViewById(R.id.app_edit_text);
		mEditText.setText(value);
		
		findViewById(R.id.app_edit_submit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (action) {
					case C.action.edit.CONF : 
						String sign = mEditText.getText().toString();
						customer.setSign(sign);
						break;
				}
				// close after edit
				doFinish();
			}
		});
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}