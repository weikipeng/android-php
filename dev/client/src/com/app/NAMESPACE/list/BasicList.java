package com.app.NAMESPACE.list;

import java.util.List;
import java.util.Map;

import com.app.NAMESPACE.filter.BasicFilter;

import android.content.Context;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BasicList extends SimpleAdapter {

	public BasicList(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public void setViewText(TextView v, String text) {
		BasicFilter.setHtml(v, text);
	}
}