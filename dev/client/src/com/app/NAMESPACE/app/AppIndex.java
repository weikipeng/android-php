package com.app.NAMESPACE.app;

import java.util.ArrayList;
import com.app.NAMESPACE.R;
import com.app.NAMESPACE.auth.AuthApp;
import com.app.NAMESPACE.base.BaseMessage;
import com.app.NAMESPACE.base.C;
import com.app.NAMESPACE.demo.DemoMap;
import com.app.NAMESPACE.demo.DemoWeb;
import com.app.NAMESPACE.list.BasicList;
import com.app.NAMESPACE.model.Customer;
import com.app.NAMESPACE.model.Menu;
import com.app.NAMESPACE.util.AppUtil;

import android.os.Bundle;
import android.view.View;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ListView;

public class AppIndex extends AuthApp {

	private TextView mTextHello;
	private TextView mTextSign;
	private ListView mListMenu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app_index);
		
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_home);
		ib.setImageResource(R.drawable.tab_home_2);
		
		TextView textIndexHello = (TextView) this.findViewById(R.id.app_index_text_hello);
		textIndexHello.setText("This is home page.");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		mTextSign = (TextView) this.findViewById(R.id.tpl_list_info_text_sign);
		mTextSign.setText(customer.getSign());
		
		// show menu list
		mListMenu = (ListView) findViewById(R.id.app_index_list_menu);
		
		final ArrayList<Menu> dataList = new ArrayList<Menu>();
		dataList.add(new Menu("App Blog List", AppBlogs.class));
		dataList.add(new Menu("App Config", AppConfig.class));
		dataList.add(new Menu("Web Demo", DemoWeb.class));
		dataList.add(new Menu("Map Demo", DemoMap.class));
		
		String[] from = {Menu.COL_NAME};
		int[] to = {R.id.tpl_list_menu_text_name};
		mListMenu.setAdapter(new BasicList(this, AppUtil.dataToList(dataList, from), R.layout.tpl_list_menu, from, to));
		mListMenu.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Menu menu = dataList.get(pos);
				forward(menu.getClazz());
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
					mTextHello = (TextView) this.findViewById(R.id.app_index_text_hello);
					mTextHello.setText("Welcome " + customer.getName() + ", blog(" + customer.getBlogcount() + ") fans(" + customer.getFanscount() + ").");
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
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}