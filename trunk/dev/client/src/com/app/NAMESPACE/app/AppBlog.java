package com.app.NAMESPACE.app;

import java.util.HashMap;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.auth.AuthApp;
import com.app.NAMESPACE.base.BaseMessage;
import com.app.NAMESPACE.base.C;
import com.app.NAMESPACE.model.Blog;
import com.app.NAMESPACE.model.Customer;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class AppBlog extends AuthApp {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.app_blog);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		Bundle params = this.getIntent().getExtras();
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("blogId", params.getString("blogId"));
		this.doTaskAsync(C.task.blogView, C.api.blogView, urlParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		
		switch (taskId) {
			case C.task.blogView:
				try {
					Blog blog = (Blog) message.getResult("Blog");
					TextView textUptime = (TextView) this.findViewById(R.id.app_blog_text_uptime);
					TextView textContent = (TextView) this.findViewById(R.id.app_blog_text_content);
					textUptime.setText(blog.getUptime());
					textContent.setText(blog.getContent());
					Customer customer = (Customer) message.getResult("Customer");
					TextView textCustomerName = (TextView) this.findViewById(R.id.app_blog_text_customer_name);
					TextView testCustomerInfo = (TextView) this.findViewById(R.id.app_blog_text_customer_info);
					textCustomerName.setText(customer.getName());
					testCustomerInfo.setText(getResources().getString(R.string.blog_fans) + " : " + customer.getFanscount());
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}