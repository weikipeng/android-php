package com.app.NAMESPACE.app;

import java.util.ArrayList;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.auth.AuthApp;
import com.app.NAMESPACE.base.BaseMessage;
import com.app.NAMESPACE.base.C;
import com.app.NAMESPACE.list.BasicList;
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
		
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_conf);
		ib.setImageResource(R.drawable.tab_conf_2);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mListConfig = (ListView) findViewById(R.id.app_config_list_conf);
		
		final ArrayList<Config> dataList = new ArrayList<Config>();
		dataList.add(new Config("Sign", customer.getSign()));
		
		String[] from = {Config.COL_NAME, Config.COL_VALUE};
		int[] to = {R.id.tpl_list_config_text_name, R.id.tpl_list_config_text_value};
		mListConfig.setAdapter(new BasicList(this, AppUtil.dataToList(dataList, from), R.layout.tpl_list_config, from, to));
		mListConfig.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				doEdit(C.action.edit.CONF, dataList.get(pos).getValue());
			}
		});
		
		this.doTaskAsync(C.task.welcome, C.api.index);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		switch (taskId) {
			case C.task.welcome:
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