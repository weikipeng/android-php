package com.app.NAMESPACE.list;

import java.util.List;
import java.util.Map;

import com.app.NAMESPACE.R;
import com.app.NAMESPACE.filter.BasicFilter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ExpandList {
	
	private LayoutInflater layout = null;
	
	private Context context = null;
	private List<? extends Map<String, ?>> dataList = null;
	private int resourceId = -1;
	private String[] dataKeys = {};
	private int[] tplKeys = {};
	
	public ExpandList (Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		// layout
		this.context = context;
		this.layout = LayoutInflater.from(context);
		// data
		this.resourceId = resource;
		this.dataList = data;
		this.dataKeys = from;
		this.tplKeys = to;
	}
	
	public View getView () {
		return layout.inflate(resourceId, null);
	}
	
	public void render (ViewGroup vg) {
		int dataId = 1;
		int dataSize = dataList.size();
		for (Map<String, ?> data : dataList) {
			View v = getView();
			// render main
			for (int i = 0; i < dataKeys.length; i++) {
				String dataKey = dataKeys[i];
				int tplKey = tplKeys[i];
				TextView tv = (TextView) v.findViewById(tplKey);
				BasicFilter.setHtml(tv, data.get(dataKey).toString());
			}
			vg.addView(v);
			// render divider
			if (dataId < dataSize) {
				View d = new TextView(context, null);
				d.setBackgroundColor(R.color.divider);
				d.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
				vg.addView(d);
			}
			// count data
			dataId++;
		}
	}
}