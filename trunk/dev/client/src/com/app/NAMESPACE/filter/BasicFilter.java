package com.app.NAMESPACE.filter;

import com.app.NAMESPACE.R;

import android.text.Html;
import android.widget.TextView;

public class BasicFilter {
	
	public static void setHtml (TextView tv, String text) {
		if (tv.getId() == R.id.tpl_list_blog_text_content ||
			tv.getId() == R.id.tpl_list_blog_text_comment ||
			tv.getId() == R.id.tpl_list_comment_content
			) {
			tv.setText(Html.fromHtml(text));
		} else {
			tv.setText(text);
		}
	}
}