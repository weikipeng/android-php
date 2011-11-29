package com.app.NAMESPACE.list;

import java.util.List;
import java.util.Map;

import com.app.NAMESPACE.R;

import android.content.Context;
import android.text.Html;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BasicList extends SimpleAdapter {

	public BasicList(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public void setViewText(TextView v, String text) {
		// filter html text
		if (v.getId() == R.id.tpl_list_blog_text_content ||
			v.getId() == R.id.tpl_list_blog_text_comment) {
			v.setText(Html.fromHtml(text));
		// default text
		} else {
			super.setViewText(v, text);
		}
	}
}