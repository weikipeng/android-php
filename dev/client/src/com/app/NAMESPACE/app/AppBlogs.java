package com.app.NAMESPACE.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.auth.AuthApp;
import com.app.NAMESPACE.base.BaseMessage;
import com.app.NAMESPACE.base.C;
import com.app.NAMESPACE.list.BasicList;
import com.app.NAMESPACE.model.Blog;
import com.app.NAMESPACE.util.AppUtil;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AppBlogs extends AuthApp {
	
	private ListView blogListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_blogs);
		
		// tab button
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_2);
		ib.setImageResource(R.drawable.tab_heart_2);
		
		// show my blog list
		HashMap<String, String> blogParams = new HashMap<String, String>();
		blogParams.put("typeId", "1");
		blogParams.put("pageId", "0");
		this.doTaskAsync(C.task.blogList, C.api.blogList, blogParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		
		switch (taskId) {
			case C.task.blogList:
				try {
					@SuppressWarnings("unchecked")
					final ArrayList<Blog> blogList = (ArrayList<Blog>) message.getResultList("Blog");
					blogListView = (ListView) this.findViewById(R.id.app_blogs_list_view);
					String[] from = {
						Blog.COL_CONTENT,
						Blog.COL_UPTIME,
						Blog.COL_COMMENT
					};
					int[] to = {
						R.id.tpl_list_blog_text_content,
						R.id.tpl_list_blog_text_uptime,
						R.id.tpl_list_blog_text_comment
					};
					blogListView.setAdapter(new BasicList(this, AppUtil.dataToList(blogList, from), R.layout.tpl_list_blog, from, to));
					blogListView.setOnItemClickListener(new OnItemClickListener(){
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
							Bundle params = new Bundle();
							params.putString("blogId", blogList.get(pos).getId());
							overlay(AppBlog.class, params);
						}
					});
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