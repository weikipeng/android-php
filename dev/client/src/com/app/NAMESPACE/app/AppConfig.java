package com.app.NAMESPACE.app;

import java.util.ArrayList;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.auth.AuthApp;
import com.app.NAMESPACE.base.BaseMessage;
import com.app.NAMESPACE.base.C;
import com.app.NAMESPACE.list.SimpleList;
import com.app.NAMESPACE.model.Config;
import com.app.NAMESPACE.model.Customer;
import com.app.NAMESPACE.util.AppUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AppConfig extends AuthApp {
	
	private ListView mListConfig;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_config);
		
		// tab button
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_3);
		ib.setImageResource(R.drawable.tab_conf_2);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// config list
		final ArrayList<Config> dataList = new ArrayList<Config>();
		dataList.add(new Config(getResources().getString(R.string.config_face), customer.getFace()));
		dataList.add(new Config(getResources().getString(R.string.config_sign), customer.getSign()));
		String[] from = {Config.COL_NAME};
		int[] to = {R.id.tpl_list_menu_text_name};
		mListConfig = (ListView) findViewById(R.id.app_config_list_main);
		mListConfig.setAdapter(new SimpleList(this, AppUtil.dataToList(dataList, from), R.layout.tpl_list_menu, from, to));
		mListConfig.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// change face
				if (pos == 0) {
					overlay(AppSetFace.class);
				// edit customer info
				} else {
					Bundle data = new Bundle();
					data.putInt("action", C.action.edittext.CONFIG);
					data.putString("value", dataList.get(pos).getValue());
					doEditText(data);
				}
			}
		});
		
		// prepare data
		this.doTaskAsync(C.task.index, C.api.index);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case C.task.index:
				try {
					Customer customer = (Customer) message.getResult("Customer");
					TextView mTextTop = (TextView) this.findViewById(R.id.tpl_list_info_text_top);
					TextView mTextBottom = (TextView) this.findViewById(R.id.tpl_list_info_text_bottom);
					mTextTop.setText(customer.getSign());
					mTextBottom.setText("Blog(" + customer.getBlogcount() + ") Fans(" + customer.getFanscount() + ").");
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.forward(AppIndex.class);
		}
		return super.onKeyDown(keyCode, event);
	}
	
}